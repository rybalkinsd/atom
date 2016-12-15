package network.handlers;

import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import utils.JSONDeserializationException;
import utils.JSONHelper;
import model.Player;

import java.util.Map;

public class PacketHandlerMove {
  public PacketHandlerMove(@NotNull Session session, @NotNull String json) {
    CommandMove commandMove;
    try {
      commandMove = JSONHelper.fromJSON(json, CommandMove.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    Player player=null;
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
        if(connection.getValue().equals(session)){
                player=connection.getKey();
        }
    }
    MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new MoveMsg(ApplicationContext.instance().get(MessageSystem.class).getService(Mechanics.class).getAddress(), commandMove, player);
    messageSystem.sendMessage(message);
  }
}
