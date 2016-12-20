package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.MoveMsg;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandMove;
import utils.json.JSONDeserializationException;
import utils.json.JSONHelper;

public class PacketHandlerMove implements PacketHandler {
    public void handle(@NotNull Session session, @NotNull String json) {
        CommandMove commandMove;
        try {
            commandMove = JSONHelper.fromJSON(json, CommandMove.class);
        } catch (JSONDeserializationException e) {
            log.fatal(e.getMessage());
            return;
        }

        log.info("Create MoveMsg");
        MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Player player = ApplicationContext.instance().get(ClientConnections.class).getPlayerBySession(session);
        if (player == null) {
            log.warn("Could not send MoveMsg, player is null");
            return;
        }
        Message message = new MoveMsg(player, commandMove);
        if (messageSystem == null) return;
        messageSystem.sendMessage(message);
    }
}
