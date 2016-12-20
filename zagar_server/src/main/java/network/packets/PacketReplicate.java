package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandReplicate;
import protocol.model.Cell;
import utils.json.JSONHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class PacketReplicate {
    @NotNull
    private static final Logger log = LogManager.getLogger(PacketReplicate.class);
    @NotNull
    private final List<Cell> cells;

    public PacketReplicate(@NotNull List<Cell> cells) {
        this.cells = cells;
    }

    public void write(@NotNull Session session) throws IOException {
        if (!session.isOpen()) return;
        String msg = JSONHelper.toJSON(new CommandReplicate(cells));
        log.trace("Sending [" + msg + "]");
        ByteArrayOutputStream bos = new ByteArrayOutputStream(msg.length());
        GZIPOutputStream gos = new GZIPOutputStream(bos);
        gos.write(msg.getBytes());
        gos.close();
        session.getRemote().sendBytes(ByteBuffer.wrap(bos.toByteArray()));
        bos.close();
    }
}
