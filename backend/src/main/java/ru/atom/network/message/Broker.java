package ru.atom.network.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.controller.GameSession;
import ru.atom.model.input.InputAction;
import ru.atom.model.input.Move;
import ru.atom.model.input.PlantBomb;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Player;
import ru.atom.util.JsonHelper;

/**
 * Created by sergey on 2/2/17.
 */
public class Broker {
    private final static Logger log = LogManager.getLogger(GameSession.class);
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

        // todo remove redundant casts
        switch (message.getTopic()) {
            case MOVE:
                InputAction move = JsonHelper.fromJson(message.getData(), Move.class);
                connectionPool.getPlayer(session).consumeInput(move);
                break;
            case PLANT_BOMB:
                InputAction plant = JsonHelper.fromJson(message.getData(), PlantBomb.class);
                connectionPool.getPlayer(session).consumeInput(plant);
                break;
        }
    }

    public void send(@NotNull Player player, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        Session session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }

}
