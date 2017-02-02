package ru.atom.network.message;

import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.input.Move;
import ru.atom.util.JsonHelper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 2/2/17.
 */
public class Broker {
    private ConcurrentHashMap<Session, Queue<Message>> messages = new ConcurrentHashMap<>();

    public void receive(@NotNull Session session, @NotNull String msg) {
        JsonObject json = JsonHelper.getJSONObject(msg);
        Topic topic = Topic.valueOf(json.get("topic").getAsString());

        switch (topic) {
            case MOVE:
                Move.from()
        }
        if (!messages.contains(session)) {
            messages.put(session, new LinkedList<>());
        }

        Message message = new Message(topic.getTopicClass(), object.get("data").getAsString());
        messages.get(session).add(message);
    }


}
