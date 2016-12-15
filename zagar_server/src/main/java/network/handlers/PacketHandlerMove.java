package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.util.Map;
import java.util.Set;

public class PacketHandlerMove {
  public PacketHandlerMove(@NotNull Session session, @NotNull String json) {
    CommandMove commandMove;
    try {
      commandMove = JSONHelper.fromJSON(json, CommandMove.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }

    Set<Map.Entry<Player, Session>> connections = ApplicationContext.instance()
            .get(ClientConnections.class).getConnections();
      for(Map.Entry<Player,Session> conn : connections){
          if (conn.getValue().equals(session)){
              Message message = new MoveMsg(conn.getKey(), commandMove.getDx(), commandMove.getDy());
              ApplicationContext.instance().get(MessageSystem.class).sendMessage(message);
              return;
          }
      }
  }
}
