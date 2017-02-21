package main.java.zagar.network.packets;

import main.java.zagar.Game;
import main.java.zagar.util.JSONHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.commands.CommandWindowSize;

import java.awt.*;
import java.io.IOException;

/**
 * Created by xakep666 on 03.12.16.
 * <p>
 * Packet with window size
 */
public class PacketWindowSize {
    @NotNull
    private static final Logger log = LogManager.getLogger(">>>");
    @NotNull
    private final Dimension size;

    public PacketWindowSize(@NotNull Dimension size) {
        this.size = size;
    }

    public void write() throws IOException {
        String msg = JSONHelper.toJSON(new CommandWindowSize(size.width, size.height));
        log.info("Sending [" + msg + "]");
        Game.socket.session.getRemote().sendString(msg);
    }
}
