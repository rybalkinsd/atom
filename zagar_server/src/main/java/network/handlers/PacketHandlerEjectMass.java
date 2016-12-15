package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import utils.JSONHelper;

import java.io.IOException;
import java.util.Map;

public class PacketHandlerEjectMass {
  public PacketHandlerEjectMass(@NotNull Session session, @NotNull String json) {
    CommandEjectMass commandEjectMass;
    try {
      commandEjectMass = (CommandEjectMass) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      ex.printStackTrace();
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
    Message message = new EjectMassMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),commandEjectMass,player);
    messageSystem.sendMessage(message);
  }
}
