package network.handlers;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.EjectMassMsg;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandEjectMass;
import utils.json.JSONDeserializationException;
import utils.json.JSONHelper;

public class PacketHandlerEjectMass implements PacketHandler {
    public void handle(@NotNull Session session, @NotNull String json) {
        CommandEjectMass commandEjectMass;
        try {
            commandEjectMass = JSONHelper.fromJSON(json, CommandEjectMass.class);
        } catch (JSONDeserializationException e) {
            e.printStackTrace();
            return;
        }

        log.info("Create EjectMassMsg");
        MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);

        Player player = ApplicationContext.instance().get(ClientConnections.class).getPlayerBySession(session);
        if (player == null) {
            log.warn("Could not send EjectMassMsg, player is null");
            return;
        }
        Message message = new EjectMassMsg(player, commandEjectMass);
        if (messageSystem == null) return;
        messageSystem.sendMessage(message);
    }
}
