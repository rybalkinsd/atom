package network.handlers;

import accountserver.api.AuthenticationServlet;
import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SplitMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import network.packets.PacketAuthFail;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandSplit;
import utils.JSONDeserializationException;
import utils.JSONHelper;

import java.io.IOException;
import java.util.Map;

public class PacketHandlerSplit {
  public PacketHandlerSplit(@NotNull Session session, @NotNull String json) {
    CommandSplit commandSplit;
    try {
      commandSplit = JSONHelper.fromJSON(json, CommandSplit.class);
    } catch (JSONDeserializationException e) {
      e.printStackTrace();
      return;
    }
    MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    if(messageSystem==null)
      return;
    ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
    Player player=null;
    for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
      if(connection.getValue().equals(session)){
        player=connection.getKey();
      }
    }
    Message message = new SplitMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),commandSplit,player);
    messageSystem.sendMessage(message);
  }
}
