package ru.atom.bombergirl.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.bombergirl.gamemodel.model.Action;
import ru.atom.bombergirl.gamemodel.model.Move;
import ru.atom.bombergirl.gamemodel.model.PlantBomb;
import ru.atom.bombergirl.message.Message;
import ru.atom.bombergirl.message.Topic;
import ru.atom.bombergirl.mmserver.Connection;
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
        if (connectionPool.getConnection(session).getPawn() != null) {
            switch (message.getTopic()) {
                case MOVE:
                    Action movement = JsonHelper.fromJson(message.getData(), Move.class);
                    connectionPool.getConnection(session).start(movement);
                    break;
                case PLANT_BOMB:
                    Action plant = JsonHelper.fromJson(message.getData(), PlantBomb.class);
                    connectionPool.getConnection(session).start(plant);
                    break;
                default:
                    log.info("Error");
            }
        }
    }

    public void send(@NotNull Connection player, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        Session session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }

}
