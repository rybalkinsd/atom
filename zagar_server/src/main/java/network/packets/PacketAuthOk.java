package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandAuthOk;
import utils.json.JSONHelper;

import java.io.IOException;

public class PacketAuthOk {
    @NotNull
    private static final Logger log = LogManager.getLogger(PacketAuthOk.class);

    public PacketAuthOk() {
    }

    public void write(@NotNull Session session) throws IOException {
        if (!session.isOpen()) return;
        String msg = JSONHelper.toJSON(new CommandAuthOk());
        log.trace("Sending [" + msg + "]");
        session.getRemote().sendString(msg);
    }
}
