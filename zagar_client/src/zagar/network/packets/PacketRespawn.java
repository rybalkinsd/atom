package zagar.network.packets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import protocol.CommandRespawn;
import zagar.Game;
import zagar.util.JSONHelper;

import java.io.IOException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;
import protocol.CommandMove;
import zagar.Game;
import zagar.util.JSONHelper;
/**
 * Created by artem on 19.12.16.
 */
public class PacketRespawn {
    @NotNull
    private static final Logger log = LogManager.getLogger(">>>");

    public PacketRespawn() {

        try {
            write();
        } catch (IOException e) {
            log.error("Failed to respawn player {}",e);
        }
    }

    public void write() throws IOException {
        String msg = JSONHelper.toSerial(new CommandRespawn());
        log.info("Sending [" + msg + "]");
        Game.socket.session.getRemote().sendString(msg);
    }
}
