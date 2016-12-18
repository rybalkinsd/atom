package main.java.zagar;

import main.java.zagar.auth.AuthClient;
import main.java.zagar.network.ServerConnectionSocket;
import main.java.zagar.network.packets.PacketEjectMass;
import main.java.zagar.network.packets.PacketMove;
import main.java.zagar.util.Reporter;
import main.java.zagar.view.Cell;
import main.java.zagar.view.GameFrame;
import main.java.zagar.view.inputforms.HostInputForm;
import main.java.zagar.view.inputforms.LoginPasswordInputForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import static main.java.zagar.GameConstants.*;

public class Game {
  @NotNull
  private static final Logger log = LogManager.getLogger(Game.class);
    @NotNull
  public static String[] leaderBoard = new String[10];
  public static double maxSizeX, maxSizeY, minSizeX, minSizeY;
  @NotNull
  public static ArrayList<Integer> playerID = new ArrayList<>();
  public static float zoom;
  public static int score;
  @NotNull
  public static ServerConnectionSocket socket;
  @NotNull
  public static String serverToken;
  @NotNull
  public static String login;
  @NotNull
  public static HashMap<Integer, String> cellNames = new HashMap<>();
  public static long fps = 60;
  public static boolean rapidEject;
  @NotNull
  public static GameState state = GameState.NOT_AUTHORIZED;
  @NotNull
  private static List<Cell> cells = new CopyOnWriteArrayList<>();
    @NotNull
    private static ConcurrentLinkedDeque<Cell> player = new ConcurrentLinkedDeque<>();
    @NotNull
    private static List<Cell> bufCells = new CopyOnWriteArrayList<>();
    @NotNull
    private static ConcurrentLinkedDeque<Cell> bufPlayer = new ConcurrentLinkedDeque<>();
    @NotNull
  public String gameServerUrl;
  @NotNull
  public AuthClient authClient;
  private double zoomm = -1;
  private int sortTimer;

  public Game() {
    HostInputForm gameServerUrlInput =
            new HostInputForm("game server",DEFAULT_GAME_SERVER_HOST,DEFAULT_GAME_SERVER_PORT);
    if (!gameServerUrlInput.showForm()) {
      System.exit(0);
    }
    this.gameServerUrl = "ws://" + gameServerUrlInput.getHost() + ":" + gameServerUrlInput.getPort();
    HostInputForm authServerUrlInput =
            new HostInputForm("authentication server",DEFAULT_ACCOUNT_SERVER_HOST,DEFAULT_ACCOUNT_SERVER_PORT);
    if (!authServerUrlInput.showForm()) {
      System.exit(0);
    }
    authClient = new AuthClient(authServerUrlInput.getHost(),authServerUrlInput.getPort());
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

  public static void sortCells() {
    cells.sort((o1, o2) -> {
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

    public static void updateBuffer(@NotNull List<Cell> cells,
                                    @NotNull ConcurrentLinkedDeque<Cell> player) {
        bufCells = cells;
        bufPlayer = player;
    }

    @NotNull
    public static ConcurrentLinkedDeque<Cell> getPlayers() {
        return player;
    }

    @NotNull
    public static List<Cell> getCells() {
        return cells;
    }

  private void authenticate() {
    while (serverToken == null) {
      AuthOption authOption = chooseAuthOption();
      if (authOption == null) {
        return;
      }
      LoginPasswordInputForm loginForm = new LoginPasswordInputForm();
      if (loginForm.showForm()) {
        Game.login = loginForm.getLogin();
        String password = loginForm.getPassword();
        switch(authOption) {

          case REGISTER:
            if (!authClient.register(login, password)) {
              Reporter.reportFail("Register failed", "Register failed");
            }//autologin after registration
            serverToken = authClient.login(Game.login, password);
            if (serverToken == null) {
              Reporter.reportWarn("Login failed", "Login failed");
            }
            break;

          case LOGIN:
            serverToken = authClient.login(Game.login, password);
            if (serverToken == null) {
              Reporter.reportWarn("Login failed", "Login failed");
              break;
            }
        }
      }
    }
  }

  @Nullable
  private AuthOption chooseAuthOption() {
    Object[] options = {AuthOption.LOGIN, AuthOption.REGISTER};
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
    return null;
  }

  public void tick() throws IOException {
    log.info("[TICK]");
      //read from buffer
      cells = new CopyOnWriteArrayList<>(bufCells);
      player = new ConcurrentLinkedDeque<>(bufPlayer);

    //moved to PacketHandlerReplicate
    /*ArrayList<Integer> toRemove = new ArrayList<>();

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
    }*/

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

      zoomm = Main.getFrame().getSize().getHeight() / (1024 / Math.pow(Math.min(64.0 / totalSize, 1), 0.4));

      if (zoomm > 1) {
        zoomm = 1;
      }

      if (zoomm == -1) {
        zoomm = zoom;
      }
      zoom += (zoomm - zoom) / 40f;

      if (socket.session.isOpen()) {
//        check if pointer inside window and window in focus
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        Rectangle bounds = Main.getFrame().getBounds();
        bounds.setLocation(Main.getFrame().getLocationOnScreen());
        if (!bounds.contains(mousePos) || !Main.getFrame().isFocused()) return;

//        //normalize to half size of window
//        float dx = (float) (mousePos.getX() - bounds.getCenterX()) / (Main.getFrame().getSize().height * zoom);
//        float dy = (float) (mousePos.getY() - bounds.getCenterY()) / (Main.getFrame().getSize().height * zoom);
//
//        new PacketMove(dx, dy).write(socket.session);
       float  dx = (float) ((mousePos.getX() - Main.getFrame().getSize().width / 2) / zoom);
       float  dy = (float) ((mousePos.getY() - Main.getFrame().getSize().height / 2) / zoom);
        float size = player.stream().map(Cell::getSize).reduce(Math::max).orElse(1f);
        (new PacketMove(dx/Math.max(Math.abs(dx)/10f,size), dy/Math.max(Math.abs(dy)/10f, size))).write(socket.session);


        if (rapidEject) {
          new PacketEjectMass().write();
        }
      }
    }

    cells.forEach(Cell::tick);

    sortTimer++;

    if (sortTimer > 10) {
      sortCells();
      sortTimer = 0;
    }

//    socket.session.getRemote().sendPing(ByteBuffer.allocate(48));
  }
  private enum AuthOption {
    REGISTER, LOGIN
  }

  public enum GameState {
    NOT_AUTHORIZED, AUTHORIZED
  }
}
