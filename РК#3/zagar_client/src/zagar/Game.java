package zagar;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import org.jetbrains.annotations.Nullable;
import zagar.auth.AuthClient;
import zagar.network.ServerConnectionSocket;
import zagar.network.packets.PacketMove;
import zagar.network.packets.PacketEjectMass;
import org.jetbrains.annotations.NotNull;
import zagar.util.Reporter;
import zagar.view.Cell;
import zagar.view.Food;
import zagar.view.GameFrame;

import static zagar.GameConstants.*;

public class Game {
  @NotNull
  private static final Logger log = LogManager.getLogger(Game.class);
  @NotNull
  public static volatile Cell[] cells = new Cell[0];
  @NotNull
  public static volatile Food[] food = new Food[0];
  @NotNull
  public static ConcurrentLinkedDeque<Cell> player = new ConcurrentLinkedDeque<>();
  @NotNull
  public static String[] leaderBoard = new String[10];
  public static double maxSizeX, maxSizeY, minSizeX, minSizeY;
  @NotNull
  public static ArrayList<Integer> playerID = new ArrayList<>();
  public static float followX;
  public static float followY;
  public static double zoom;
  public static int score;
  @NotNull
  public static ServerConnectionSocket socket;
  @NotNull
  public static String serverToken;
  @NotNull
  public static String login = DEFAULT_LOGIN;
  public static int spawnPlayer = -1;
  @NotNull
  public static HashMap<Integer, String> cellNames = new HashMap<>();
  public static long fps = 60;
  public static boolean rapidEject;
  @NotNull
  public static GameState state = GameState.NOT_AUTHORIZED;
  private double zoomm = -1;
  private int sortTimer;
  @NotNull
  public String gameServerUrl;
  @NotNull
  public AuthClient authClient = new AuthClient();

  public Game() {
    selectHost();

    this.spawnPlayer = 100;

    final WebSocketClient client = new WebSocketClient();
    try {
      ClientUpgradeRequest request = new ClientUpgradeRequest();
      request.setHeader("Origin", "zagar.io");
      this.socket = new ServerConnectionSocket();
      while (Game.state.equals(GameState.NOT_AUTHORIZED)) {
        Game.serverToken = null;
        authenticate();
        URI serverURI = new URI(gameServerUrl + "/clientConnection");
        Thread thread = new Thread(() -> {
          try {
            client.start();
            client.connect(socket, serverURI, request);
            log.info("Trying to connect <" + gameServerUrl + ">");
            socket.awaitClose(7, TimeUnit.DAYS);
          } catch (Throwable t) {
            t.printStackTrace();
          }
          Game.state = GameState.NOT_AUTHORIZED;
        });
        Game.state = GameState.CONNECTING;
        thread.start();
        for (int j = 0; j < 10; j++) {
          Thread.sleep(500);
          if (Game.state != GameState.CONNECTING)
            break;
        }
        if (Game.state == GameState.NOT_AUTHORIZED)
          Reporter.reportWarn("Connection failed", "Server rejected your connection");
        if (Game.state == GameState.CONNECTING) {
          Reporter.reportWarn("Connection failed", "Connection TIME OUT");
          Game.state=GameState.NOT_AUTHORIZED;
        }
        if(Game.state!=GameState.AUTHORIZED){
          client.stop();
          thread.interrupt();
        }
      }
    }catch(Throwable t)
    {t.printStackTrace();}
  }

  private void selectHost()
  {String accountServiceUrl;
    AuthClient.setServiceUrl("http://" + (accountServiceUrl=JOptionPane.showInputDialog(null, "Account server", DEFAULT_ACCOUNT_SERVER_HOST + ":" + DEFAULT_ACCOUNT_SERVER_PORT)));
    this.gameServerUrl = "ws://" + (JOptionPane.showInputDialog(null, "Game server", accountServiceUrl==null?(DEFAULT_GAME_SERVER_HOST + ":" + DEFAULT_GAME_SERVER_PORT):(accountServiceUrl.indexOf(':')<0?(accountServiceUrl):(accountServiceUrl.substring(0,accountServiceUrl.indexOf(':')) + ":" + DEFAULT_GAME_SERVER_PORT))));
  }

  private void authenticate() {
    while (serverToken == null) {
      AuthOption authOption = chooseAuthOption();
      if (authOption == null) {
        return;
      }
      if (authOption == AuthOption.REGISTER||authOption == AuthOption.LOGIN)
      {
        String password=null;
        login = JOptionPane.showInputDialog(null, "Login", DEFAULT_LOGIN);
        if(login!=null)
          password = (JOptionPane.showInputDialog(null, "Password", DEFAULT_PASSWORD));

        if (password != null && login != null) {
          if (authOption == AuthOption.REGISTER) {
            if (!authClient.register(login, password)) {
              Reporter.reportFail("Register failed", "Register failed");
            }
          } else if (authOption == AuthOption.LOGIN) {
            serverToken = authClient.login(Game.login, password);
            if (serverToken == null) {
              Reporter.reportWarn("Login failed", "Login failed");
            }
          }
        }
      }
      else if (authOption == AuthOption.CHANGE_HOST){
        String accountServiceUrl;
        AuthClient.setServiceUrl("http://" + (accountServiceUrl=JOptionPane.showInputDialog(null, "Account server", DEFAULT_ACCOUNT_SERVER_HOST + ":" + DEFAULT_ACCOUNT_SERVER_PORT)));
        this.gameServerUrl = "ws://" + (JOptionPane.showInputDialog(null, "Game server", accountServiceUrl==null?
                (DEFAULT_GAME_SERVER_HOST + ":" + DEFAULT_GAME_SERVER_PORT): (accountServiceUrl.indexOf(':')<0?
                (accountServiceUrl):(accountServiceUrl.substring(0,accountServiceUrl.indexOf(':')) + ":" + DEFAULT_GAME_SERVER_PORT))));
      }
    }
  }

  @Nullable
  private AuthOption chooseAuthOption() {
    Object[] options = {AuthOption.LOGIN, AuthOption.REGISTER, "CHANGE HOST"};
    int authOption = JOptionPane.showOptionDialog(null,
        "Choose authentication option",
        "Authentication",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[1]);

    if (authOption == 0) {
      return AuthOption.LOGIN;
    }
    if (authOption == 1) {
      return AuthOption.REGISTER;
    }
    if (authOption == 2)
      return AuthOption.CHANGE_HOST;
    return null;
  }

  public void tick() throws IOException {
    System.out.println("CELLS:\n" + Arrays.toString(Game.cells));
    System.out.println("FOODS:\n" + Arrays.toString(Game.food));
    if (socket != null && socket.session != null && socket.session.isOpen()) {
      if (spawnPlayer != -1) {
        spawnPlayer--;
      }

      if (spawnPlayer == 0) {
        log.info("Resetting level (death)");
      }
      if (Game.player.size() == 0) {
        if (socket.session.isOpen() && spawnPlayer == -1) {
          score = 0;
          Game.player.clear();
          Game.cells = new Cell[Game.cells.length];
          cellNames.clear();
        }
      }
    }

    ArrayList<Integer> toRemove = new ArrayList<>();

    for (int i : playerID) {
      for (Cell c : Game.cells) {
        if (c != null) {
          if (c.id == i && !player.contains(c)) {
            log.info("Centered cell " + c.name);
            player.add(c);
            toRemove.add(i);
          }
        }
      }
    }

    for (int i : toRemove) {
      playerID.remove(playerID.indexOf(i));
    }

    if (socket.session != null && player.size() > 0) {
      float totalSize = 0;
      int newScore = 0;
      for (Cell c : player) {
        totalSize += c.size;
        newScore += (c.size * c.size) / 100;
      }

      if (newScore > score) {
        score = newScore;
      }

      zoomm = GameFrame.size.height / (1024 / Math.pow(Math.min(64.0 / totalSize, 1), 0.4));

      if (zoomm > 1) {
        zoomm = 1;
      }

      if (zoomm == -1) {
        zoomm = zoom;
      }
      zoom += (zoomm - zoom) / 40f;

      if (socket.session.isOpen()) {
        float avgX = 0;
        float avgY = 0;
        totalSize = 0;

        for (Cell c : Game.player) {
          avgX += c.x;
          avgY += c.y;
          totalSize += c.size;
        }

        avgX /= Game.player.size();
        avgY /= Game.player.size();

        float x = avgX;
        float y = avgY;
        x += (float) ((GameFrame.mouseX - GameFrame.size.width / 2) / zoom);
        y += (float) ((GameFrame.mouseY - GameFrame.size.height / 2) / zoom);
        followX = x;
        followY = y;
        (new PacketMove(x, y)).write();

        if (rapidEject) {
          new PacketEjectMass().write();
        }
      }
    }

    for (int i = 0; i < cells.length; i++) {
      if (cells[i] != null) {
        cells[i].tick();
      }
    }

    sortTimer++;

    if (sortTimer > 10) {
      sortCells();
      sortTimer = 0;
    }
  }

  public static void sortCells() {
    Arrays.sort(cells, (o1, o2) -> {
      if (o1 == null && o2 == null) {
        return 0;
      }
      if (o1 == null) {
        return 1;
      }
      if (o2 == null) {
        return -1;
      }
      return Float.compare(o1.size, o2.size);
    });
  }

  public static void respawn() {
    if (spawnPlayer == -1) {
      spawnPlayer = 100;
    }
  }

  private enum AuthOption {
    REGISTER, LOGIN, CHANGE_HOST
  }

  public enum GameState {
    NOT_AUTHORIZED, AUTHORIZED, CONNECTING
  }
}
