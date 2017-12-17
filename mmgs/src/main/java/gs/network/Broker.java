package gs.network;

import gs.GameSession;
import gs.inputqueue.InputQueue;
import gs.message.Message;
import gs.message.Topic;
import gs.GameMechanics;
import gs.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


public class Broker {
    private static final Logger log = LogManager.getLogger(Broker.class);

    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;

    public static Broker getInstance() {
        return instance;
    }

    private InputQueue inputQueue = new InputQueue();

    public Broker() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public void receive(@NotNull WebSocketSession session, @NotNull String msg) {
        Message message = JsonHelper.fromJson(msg, Message.class);
        message.setOwner(ConnectionPool.getInstance().getPlayer(session));
        inputQueue.addToQueue(message);
    }

    public void send(@NotNull GameSession gs, @NotNull String player, @NotNull Topic topic, @NotNull String message) {
        String msg = "{\"topic\":\"" + topic + "\",\"data\":{\"objects\":[" + message + "]}}";
        TextMessage msgToJson = new TextMessage(msg);
        try {
            gs.getSession(player).sendMessage(msgToJson);
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

}
