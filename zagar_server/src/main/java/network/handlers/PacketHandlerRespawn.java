package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.RespawnMsg;
import model.Player;
import network.ClientConnectionServer;
import network.ClientConnections;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by artem on 19.12.16.
 */
public class PacketHandlerRespawn {
    public PacketHandlerRespawn(@NotNull Session session){
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
        Message message = new RespawnMsg(messageSystem.getService(ClientConnectionServer.class).getAddress(),player);
        messageSystem.sendMessage(message);
    }
}
