package ru.atom.websocket.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.websocket.message.Message;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.model.Movable;
import ru.atom.websocket.server.GameManager;
import ru.atom.websocket.util.JsonHelper;

import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    private static final Logger log = LogManager.getLogger(Broker.class);
    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;
    private final GameManager gameManager;

    public static Broker getInstance() {
        return instance;
    }

    private Broker() {
        this.connectionPool = ConnectionPool.getInstance();
        this.gameManager = GameManager.getInstance();
    }

    public void receive(@NotNull Session session, @NotNull String msg) {
        Message message = JsonHelper.fromJson(msg, Message.class);
        log.info("RECEIVED: topic {} with data {}", message.getTopic(), message.getData());
        if (message.getTopic() == Topic.PLANT_BOMB) {
            // TODO: 04.05.17   так не хорошо(( получается, что 1 менеджер обрабатывает сообщения на все игры...
            gameManager.plantBomb(session);
        } else if (message.getTopic() == Topic.MOVE) {
            gameManager.move(session, Movable.Direction.forValue(message.getData()));
        } else if (message.getTopic() == Topic.HELLO) {
            String login = message.getData();
            connectionPool.add(session, login);
        } else if (message.getTopic() == Topic.FINISH) {
            connectionPool.remove(session);
        } else {
            log.info("RECEIVED: " + msg);
        }
        //connectionPool.loggingEnterance(session);
    }

    public void send(@NotNull String player, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        log.info("Send: {} to player {}", message, player);
        Session session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.getJsonNode(JsonHelper.toJson(object))));
        log.info("broadcast: {}", message);
        connectionPool.broadcast(message);
    }

    public void broadcast(@NotNull ConcurrentHashMap<Session, String> localPool, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.getJsonNode(JsonHelper.toJson(object))));
        log.info("localBroadcast: {}", message);
        connectionPool.localBroadcast(localPool, message);
    }

}
