package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SplitMsg;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandSplit;
import utils.json.JSONDeserializationException;
import utils.json.JSONHelper;

public class PacketHandlerSplit implements PacketHandler {
    public void handle(@NotNull Session session, @NotNull String json) {
        CommandSplit commandSplit;
        try {
            commandSplit = JSONHelper.fromJSON(json, CommandSplit.class);
        } catch (JSONDeserializationException e) {
            log.fatal(e.getMessage());
            return;
        }

        log.info("Create SplitMsg");
        MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Player player = ApplicationContext.instance().get(ClientConnections.class).getPlayerBySession(session);
        if (player == null) {
            log.warn("Could not send SplitMsg, player is  null");
            return;
        }
        Message message = new SplitMsg(player, commandSplit);
        if (messageSystem == null) return;
        messageSystem.sendMessage(message);
    }
}
