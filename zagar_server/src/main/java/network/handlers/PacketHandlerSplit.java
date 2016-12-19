package network.handlers;

import main.ApplicationContext;
import main.MasterServer;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SplitMsg;
import model.Player;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandSplit;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.util.Map;

public class PacketHandlerSplit {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  public PacketHandlerSplit(@NotNull Session session, @NotNull String json) {
    CommandSplit commandSplit;
    try {
      commandSplit = JSONHelper.fromJSON(json, CommandSplit.class);
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
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message  = new SplitMsg(player);
    messageSystem.sendMessage(message);

  }
}
