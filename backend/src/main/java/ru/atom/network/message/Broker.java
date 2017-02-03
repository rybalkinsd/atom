package ru.atom.network.message;

import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.input.Move;
import ru.atom.network.ConnectionPool;
import ru.atom.util.JsonHelper;

/**
 * Created by sergey on 2/2/17.
 */
public class Broker {
//    private ConcurrentHashMap<Session, Queue<Message>> messages = new ConcurrentHashMap<>();

    public void receive(@NotNull Session session, @NotNull String msg) {
        JsonNode json = JsonHelper.getJsonNode(msg);
        Topic topic = Topic.valueOf(json.get("topic").asText());

        switch (topic) {
            case MOVE:
                Move move = JsonHelper.fromJson(json.get("data").toString(), Move.class);
                ConnectionPool.get(session).getPawn().addInput(move);
        }

    }

}
