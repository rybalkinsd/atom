package ru.atom.lecture08.websocket.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.message.Topic;
import ru.atom.lecture08.websocket.util.JsonHelper;

public class Broker {
    private static final Logger log = LogManager.getLogger(Broker.class);

    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;

    public static Broker getInstance() {
        return instance;
    }

    private Broker() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public void receive(@NotNull WebSocketSession session, @NotNull String msg) {
        log.info("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        //TODO TASK2 implement message processing
    }

    public void send(@NotNull String player, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        WebSocketSession session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }

}
