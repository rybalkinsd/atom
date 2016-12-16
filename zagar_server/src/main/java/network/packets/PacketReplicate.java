package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandReplicate;
import protocol.model.Cell;
import protocol.model.Food;
import utils.json.JSONHelper;

import java.io.IOException;
import java.util.List;

public class PacketReplicate {
    @NotNull
    private static final Logger log = LogManager.getLogger(PacketReplicate.class);
    @NotNull
    private final List<Cell> cells;
    private final List<Food> food;

    public PacketReplicate(@NotNull List<Cell> cells, List<Food> food) {
        this.cells = cells;
        this.food = food;
    }

    public void write(@NotNull Session session) throws IOException {
        if (!session.isOpen()) return;
        String msg = JSONHelper.toJSON(new CommandReplicate(food, cells));
        log.trace("Sending [" + msg + "]");
        session.getRemote().sendString(msg);
    }
}
