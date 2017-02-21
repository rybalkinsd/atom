package network;

import matchmaker.MatchMaker;
import accountserver.api.TokenContainer;
import com.google.gson.JsonObject;
import main.ApplicationContext;
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
    log.info("Received packet: " + message);
    if (getSession().isOpen()) {
      handlePacket(message);
    }
  }

  @Override
  public void onWebSocketClose(int statusCode, @NotNull String reason) {
    super.onWebSocketClose(statusCode, reason);
    log.info("Socket closed: [" + statusCode + "] " + reason);
    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    MatchMaker mm = ApplicationContext.instance().get(MatchMaker.class);
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
      if(!connection.getValue().isOpen()){
        clientConnections.removeConnection(connection.getKey());
        log.info("Connection removed " + connection.getKey());
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
          for (Player e: gameSession.getPlayers()){
            if (connection.getKey() == e){
              gameSession.leave(connection.getKey());
            }
          }
        }
        try {
          TokenContainer.removeToken(TokenContainer.getTokenByUsername(connection.getKey().getName()));
        }
        catch (Exception e){
          log.info("Token deleting failed");
          log.error(e);
        }
      }
    }

  }

  @Override
  public void onWebSocketError(@NotNull Throwable cause) {
    super.onWebSocketError(cause);
    cause.printStackTrace(System.err);
  }

  public void handlePacket(@NotNull String msg) {
    JsonObject json = JSONHelper.getJSONObject(msg);
    String name = json.get("command").getAsString();
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
      case CommandEjectVirus.NAME:
        new PacketHandlerEjectVirus(getSession(), msg);
        break;

    }
  }
}
