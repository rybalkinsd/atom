package network.handlers;

import main.ApplicationContext;
import model.Player;
import network.ClientConnections;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandWindowSize;
import utils.json.JSONDeserializationException;
import utils.json.JSONHelper;

public class PacketHandlerWindowSize implements PacketHandler {
    public void handle(@NotNull Session session, @NotNull String json) {
        CommandWindowSize commandWindowSize;
        try {
            commandWindowSize = JSONHelper.fromJSON(json, CommandWindowSize.class);
        } catch (JSONDeserializationException e) {
            e.printStackTrace();
            return;
        }
        Player player = ApplicationContext.instance().get(ClientConnections.class).getPlayerBySession(session);
        if (player == null) {
            log.warn("Cannot set player window size, player is null");
            return;
        }
        player.setWindowWidth(commandWindowSize.getWidth());
        player.setWindowHeight(commandWindowSize.getHeight());
    }
}
