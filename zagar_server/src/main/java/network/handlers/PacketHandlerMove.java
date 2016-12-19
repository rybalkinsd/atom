package network.handlers;

import network.ClientConnectionServer;
import network.ClientConnections;
import main.ApplicationContext;
import mechanics.Mechanics;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import model.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import utils.JSONDeserializationException;
import utils.JSONHelper;


public class PacketHandlerMove {
    public PacketHandlerMove(@NotNull Session session, @NotNull String json) {
        CommandMove commandMove;
        try {
            commandMove = JSONHelper.fromJSON(json, CommandMove.class);
        } catch (JSONDeserializationException e) {
            e.printStackTrace();
            return;
        }
        MessageSystem messageSystem = ApplicationContext.get(MessageSystem.class);
        Address from = messageSystem.getService(ClientConnectionServer.class).getAddress();
        Address to = messageSystem.getService(Mechanics.class).getAddress();
        Player currentPlayer = ApplicationContext.get(ClientConnections.class).getConnectedPlayer(session);
        Message message = new MoveMsg(from, to, currentPlayer, commandMove.getDx(), commandMove.getDy() );
        messageSystem.sendMessage(message);
    }
}
