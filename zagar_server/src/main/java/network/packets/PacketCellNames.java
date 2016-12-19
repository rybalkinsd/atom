package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.CommandCellNames;
import protocol.model.CellsName;
import utils.JSONHelper;

import java.io.IOException;

/**
 * Created by eugene on 12/17/16.
 */
public class PacketCellNames {
    @NotNull
    private static final Logger log = LogManager.getLogger(PacketCellNames.class);
    @NotNull
    private final CellsName[] cells;


    public PacketCellNames(@NotNull CellsName[] cells) {
        this.cells = cells;
    }

    public void write(@NotNull Session session) throws IOException {
        String msg = JSONHelper.toJSON(new CommandCellNames(cells));
        log.info("Sending [" + msg + "]");
        session.getRemote().sendString(msg);
    }
}
