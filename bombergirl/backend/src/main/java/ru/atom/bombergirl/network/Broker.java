package ru.atom.bombergirl.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.bombergirl.message.Message;
import ru.atom.bombergirl.message.Topic;
import ru.atom.bombergirl.util.JsonHelper;

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

    public void receive(@NotNull Session session, @NotNull String msg) {
        log.info("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        switch (message.getTopic()) {
            case MOVE:
                log.info("MOVE");
                break;
            case PLANT_BOMB:
                log.info("PLANT_BOMB");
                break;
            default:
                log.info("Error");
        }
        //TODO TASK2 implement message processing
    }

    public void send(@NotNull String player, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        Session session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }

}
