/*package bomber.matchmaker;


import bomber.gameserver.websocket.EventHandler;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class WebSocketClient {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WebSocketClient.class);
    private static final String name = "c";

    public static void main(String[] args) {
        WebSocketTestConnection();
    }

    public static void WebSocketTestConnection() {
        // connection url
        String uri = "ws://localhost:8090/game/connect?gameId=" + 42 + "&name=" + name;

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            EventHandler socket = new EventHandler();
            client.doHandshake(socket, uri);
        } catch (Throwable t) {
            log.info("failed to do a handshake");
        }
    }
}*/
