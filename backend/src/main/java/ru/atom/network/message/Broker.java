package ru.atom.network.message;

import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.input.Move;
import ru.atom.model.input.PlantBomb;
import ru.atom.network.ConnectionPool;
import ru.atom.util.JsonHelper;

/**
 * Created by sergey on 2/2/17.
 */
public class Broker {
    public static void receive(@NotNull Session session, @NotNull String msg) {
        Message message = JsonHelper.fromJson(msg, Message.class);

        // todo remove redundant casts
        switch (message.getTopic()) {
            case MOVE:
                Move move = JsonHelper.fromJson(message.getData(), Move.class);
                ConnectionPool.get(session).getPawn().addInput(move);
            case PLANT_BOMB:
                PlantBomb plant = JsonHelper.fromJson(message.getData(), PlantBomb.class);
                ConnectionPool.get(session).getPawn().addInput(plant);
        }
    }

    public void send(@NotNull Session session, @NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        ConnectionPool.send(session, message);
    }

    public static void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        ConnectionPool.broadcast(message);
    }

}
