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
import zagar.util.Colors;
import zagar.util.Reporter;
import zagar.view.Cell;
import zagar.view.GameFrame;

import static zagar.GameConstants.*;

public class Game {
  @NotNull
  private static final Logger log = LogManager.getLogger(Game.class);
  @NotNull
  public static volatile Cell[] cells = new Cell[0];
  @NotNull
  public static volatile Cell[] foods = new Cell[0];
  public static int playerID = 0;
  @NotNull
  public static ConcurrentLinkedDeque<Cell> playerCells = new ConcurrentLinkedDeque<>();
  @NotNull
  public static String[] leaderBoard = new String[10];
  public static double maxSizeX, maxSizeY, minSizeX, minSizeY;
  @NotNull
  public static ArrayList<Integer> playerCIDs = new ArrayList<>();
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
  @NotNull
  public static HashMap<Integer, String> cellNames = new HashMap<>();
  @NotNull
  public static HashMap<Integer,Colors> playerColors = new HashMap<>();

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
    authenticate();

    final WebSocketClient client = new WebSocketClient();
    socket = new ServerConnectionSocket();
    new Thread(() -> {
      try {
        client.start();
        URI serverURI = new URI(gameServerUrl + "/clientConnection");
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        request.setHeader("Origin", "zagar.io");
        client.connect(socket, serverURI, request);
        log.info("Trying to connect <" + gameServerUrl + ">");
        socket.awaitClose(7, TimeUnit.DAYS);
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }).start();
  }

  private void selectHost()
  {String accountServiceUrl;
    AuthClient.setServiceUrl("http://" + (accountServiceUrl=JOptionPane.showInputDialog(null, "Authentication server", DEFAULT_ACCOUNT_SERVER_HOST + ":" + DEFAULT_ACCOUNT_SERVER_PORT)));
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
    Object[] options = {AuthOption.LOGIN, AuthOption.REGISTER,"CHANGE HOST"};
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
    log.info("[TICK]");
    ArrayList<Integer> toRemove = new ArrayList<>();

    for (int i : playerCIDs) {
      for (Cell c : Game.cells) {
        if (c != null) {
          if (c.id == i && !playerCells.contains(c)) {
            log.info("Centered cell " + c.name);
            playerCells.add(c);
            toRemove.add(i);
          }
        }
      }
    }

    for (int i : toRemove) {
      playerCIDs.remove(playerCIDs.indexOf(i));
    }

    if (socket.session != null && playerCells.size() > 0) {
      float totalSize = 0;
      int newScore = 0;
      for (Cell c : playerCells) {
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

        for (Cell c : Game.playerCells) {
          avgX += c.x;
          avgY += c.y;
          totalSize += c.size;
        }

        avgX /= Game.playerCells.size();
        avgY /= Game.playerCells.size();

        float x = avgX;
        float y = avgY;
        x = (float) ((GameFrame.mouseX - GameFrame.size.width / 2) / zoom);
        y = (float) ((GameFrame.mouseY - GameFrame.size.height / 2) / zoom);

        followX = x;
        followY = y;

        float size = playerCells.stream().map(Cell::getSize).reduce(Math::max).orElse(1f);
        (new PacketMove(x/Math.max(Math.abs(x)/10f, size), y/Math.max(Math.abs(y)/10f, size))).write(socket.session);

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

  private enum AuthOption {
    REGISTER, LOGIN, CHANGE_HOST
  }

  public enum GameState {
    NOT_AUTHORIZED, AUTHORIZED
  }
}
