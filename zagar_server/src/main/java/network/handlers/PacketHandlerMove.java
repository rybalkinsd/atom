package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import utils.JSONHelper;

import java.io.IOException;
import java.util.Map;

public class PacketHandlerMove {
  @NotNull
  private final Logger log = LogManager.getLogger(PacketHandlerMove.class);
  public PacketHandlerMove(@NotNull Session session, @NotNull String json) {
    CommandMove commandMove;
    try {
      commandMove = (CommandMove) JSONHelper.fromSerial(json);
    } catch (IOException | ClassNotFoundException ex ){
      log.error("Failed to deserialize move command",ex);
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
    Message message = new MoveMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),commandMove,player);
    messageSystem.sendMessage(message);
  }
}
