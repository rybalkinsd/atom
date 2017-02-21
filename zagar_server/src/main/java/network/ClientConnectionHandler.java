package network;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.handlers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.jetbrains.annotations.NotNull;
import protocol.*;
import utils.JSONHelper;

import java.util.Map;

public class ClientConnectionHandler extends WebSocketAdapter {
  private final static @NotNull Logger log = LogManager.getLogger(ClientConnectionHandler.class);

  @Override
  public void onWebSocketConnect(@NotNull Session sess) {
    super.onWebSocketConnect(sess);
    log.info("Socket connected: " + sess);
  }

  @Override
  public void onWebSocketText(@NotNull String message) {
    super.onWebSocketText(message);
    //log.info("Received packet: " + message);
    if (getSession().isOpen()) {
      handlePacket(message);
    }
  }

  @Override
  public void onWebSocketClose(int statusCode, @NotNull String reason) {
    log.info("Socket closed: [" + statusCode + "] " + reason);
    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
      if(connection.getValue().equals(getSession())){
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
          if (gameSession.getPlayers().contains(connection.getKey())){
            gameSession.leave(connection.getKey());
          }
        }
        clientConnections.removeConnection(connection.getKey());
      }
    }
    super.onWebSocketClose(statusCode, reason);
  }

  @Override
  public void onWebSocketError(@NotNull Throwable cause) {
    super.onWebSocketError(cause);
    log.error("Web socket error",cause,System.err);
  }

  public void handlePacket(@NotNull String msg) {
    try {
      Command com = (Command) JSONHelper.fromSerial(msg);
      String name = com.getCommand();
      switch (name) {
        case CommandAuth.NAME:
          new PacketHandlerAuth(getSession(), msg);
          break;
        case CommandEjectMass.NAME:
          new PacketHandlerEjectMass(getSession(), msg);
          break;
        case CommandMove.NAME:
          new PacketHandlerMove(getSession(), msg);
          break;
        case CommandSplit.NAME:
          new PacketHandlerSplit(getSession(), msg);
          break;
        case CommandRespawn.NAME:
          new PacketHandlerRespawn(getSession());
          break;
      }
    }catch(Exception ignored){}
  }

}
