package network.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

/**
 * Created by xakep666 on 28.11.16.
 * <p>
 * Interface for packet handlers
 */
public interface PacketHandler {
    @NotNull
    Logger log = LogManager.getLogger(PacketHandler.class);

    void handle(@NotNull Session session, @NotNull String message);
}
