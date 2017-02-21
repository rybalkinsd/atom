package network.handlers;

import main.ApplicationContext;
import matchmaker.MatchMakerImpl;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import utils.JSONHelper;

import java.io.IOException;
import java.util.Map;

public class PacketHandlerEjectMass {
  @NotNull
  private final Logger log = LogManager.getLogger(PacketHandlerEjectMass.class);
  public PacketHandlerEjectMass(@NotNull Session session, @NotNull String json) {
    CommandEjectMass commandEjectMass;
    try {
      commandEjectMass = (CommandEjectMass) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("Failed to deserialize eject mass command",ex);
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
