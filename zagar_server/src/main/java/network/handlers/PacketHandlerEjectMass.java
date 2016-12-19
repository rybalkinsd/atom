package network.handlers;

import main.ApplicationContext;
import main.MasterServer;
import matchmaker.MatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import model.Field;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.util.Map;

public class PacketHandlerEjectMass {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  public PacketHandlerEjectMass(@NotNull Session session, @NotNull String json) {
    CommandEjectMass commandEjectMass;
    try {
      commandEjectMass = JSONHelper.fromJSON(json, CommandEjectMass.class);
    } catch (JSONDeserializationException e) {
      log.error(e);
      return;
    }

    Player player = null;

    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
      if(connection.getValue().equals(session)){
        player = connection.getKey();
        break;
      }
    }

    if (player == null){
      return;
    }

    Field field = null;
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      for (Player e: gameSession.getPlayers()){
        if (player == e){
          field = gameSession.getField();
        }
      }
    }

    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new EjectMassMsg(player, field);
    messageSystem.sendMessage(message);

  }
}
