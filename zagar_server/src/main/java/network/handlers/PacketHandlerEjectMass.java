package network.handlers;

import network.ClientConnectionServer;
import network.ClientConnections;
import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import model.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

public class PacketHandlerEjectMass {
    public PacketHandlerEjectMass(@NotNull Session session, @NotNull String json) {

        MessageSystem messageSystem = ApplicationContext.get(MessageSystem.class);
        Address from = messageSystem.getService(ClientConnectionServer.class).getAddress();
        Address to = messageSystem.getService(Mechanics.class).getAddress();
        Player currentPlayer = ApplicationContext.get(ClientConnections.class).getConnectedPlayer(session);
        Message message = new EjectMassMsg(from, to, currentPlayer );
        messageSystem.sendMessage(message);
    }
}
