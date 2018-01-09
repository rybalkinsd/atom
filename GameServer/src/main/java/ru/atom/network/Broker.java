package ru.atom.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.input.InputMessages;
import ru.atom.message.Message;
import ru.atom.message.Topic;
import ru.atom.util.JsonHelper;
import ru.atom.util.QueryProcessor;

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
        //log.info("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        String playerName = QueryProcessor.process(session.getUri().getQuery()).get("name");
        InputMessages.getInstance().get(playerName).add(message);
    }

    public void send(@NotNull String playerName, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        WebSocketSession session = connectionPool.getSession(playerName);
        connectionPool.send(session, message);
    }

    public synchronized void send(@NotNull String playerName, @NotNull Message message) {
        String msg = JsonHelper.toJson(message);
        WebSocketSession session = connectionPool.getSession(playerName);
        connectionPool.send(session, msg);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }

}
