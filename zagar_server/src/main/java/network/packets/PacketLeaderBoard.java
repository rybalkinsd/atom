package network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandLeaderBoard;
import utils.json.JSONHelper;

import java.io.IOException;

public class PacketLeaderBoard {
    @NotNull
    private static final Logger log = LogManager.getLogger(PacketLeaderBoard.class);
    @NotNull
    private final String[] leaderBoard;

    public PacketLeaderBoard(@NotNull String[] leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public void write(@NotNull Session session) throws IOException {
        if (!session.isOpen()) return;
        String msg = JSONHelper.toJSON(new CommandLeaderBoard(leaderBoard));
        log.trace("Sending [" + msg + "]");
        session.getRemote().sendString(msg);
    }
}
