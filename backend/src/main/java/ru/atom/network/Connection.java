package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Connection {
    private static final Logger log = LogManager.getLogger(Connection.class);
    private static WebSocketClient client = new WebSocketClient();
    private static ServerConnectionSocket socket = new ServerConnectionSocket();

//    new Thread(() -> {
//        try {
//            client.start();
//            URI serverURI = new URI(gameServerUrl + "/clientConnection");
//            ClientUpgradeRequest request = new ClientUpgradeRequest();
//            request.setHeader("Origin", "zagar.io");
//            client.connect(socket, serverURI, request);
//            log.info("Trying to connect <" + gameServerUrl + ">");
//            socket.awaitClose(7, TimeUnit.DAYS);
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//    }).start();
}
