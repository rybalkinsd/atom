package network.handlers;

import main.ApplicationContext;
import messageSystem.MessageSystem;
import messageSystem.messages.SplitMsg;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandSplit;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.util.Map;
import java.util.Set;

public class PacketHandlerSplit {
  public PacketHandlerSplit(@NotNull Session session, @NotNull String json) {
    CommandSplit commandSplit;
    try {
      commandSplit = JSONHelper.fromJSON(json, CommandSplit.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }

    Set<Map.Entry<Player, Session>> connections = ApplicationContext.instance().get(ClientConnections.class).getConnections();
    for(Map.Entry<Player,Session> conn : connections){
      if (conn.getValue().equals(session)){
        ApplicationContext.instance().get(MessageSystem.class).sendMessage(new SplitMsg(conn.getKey()));
        return;
      }
    }

  }
}
