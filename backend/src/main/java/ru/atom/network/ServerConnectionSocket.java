package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by sergei-r on 11.01.17.
 */
@WebSocket(maxTextMessageSize = 1024)
class ServerConnectionSocket {
    @NotNull
    private static final Logger log = LogManager.getLogger("<<<");

//    @NotNull
//    private final CountDownLatch closeLatch;
    @NotNull
    public Session session;

    public ServerConnectionSocket() {
//        this.closeLatch = new CountDownLatch(1);
    }

//    public boolean awaitClose(int duration, @NotNull TimeUnit unit) throws InterruptedException {
//        return this.closeLatch.await(duration, unit);
//    }

    @OnWebSocketClose
    public void onClose(int statusCode, @NotNull String reason) {
        log.info("Closed." + statusCode + "<" + reason + ">");
//        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(@NotNull Session session) throws IOException {
        this.session = session;
        log.info("Connected!");
//        new PacketAuth(Game.login, Game.serverToken).write();
    }

    @OnWebSocketMessage
    public void onTextPacket(@NotNull String msg) {
        log.info("Received packet: " + msg);
//        if (session.isOpen()) {
//            handlePacket(msg);
//        }
    }
}
