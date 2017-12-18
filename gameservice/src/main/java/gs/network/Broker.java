package gs.network;

import gs.message.Message;
import gs.message.Topic;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;
import gs.util.JsonHelper;

import javax.validation.constraints.NotNull;

public class Broker {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Broker.class);

    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;

    private Broker() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public static Broker getInstance() {
        return instance;
    }

    public void receive(@NotNull WebSocketSession session, @NotNull String msg) {
        log.info("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        //TODO TASK2 implement gs.message processing
    }

    public void send(@NotNull WebSocketSession session, @NotNull Topic topic, @NotNull Object object) {
        long time = System.currentTimeMillis();
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }
}
