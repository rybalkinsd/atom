package ru.atom.network.message;

import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 2/2/17.
 */
public class Broker {
    private ConcurrentHashMap<Session, Queue<Message<?>>> messages = new ConcurrentHashMap<>();

    public void recieve(@NotNull Session session,@NotNull String message) {
//        JsonHelper.fromJSON(Messa)
        if (!messages.contains(session)) {
            messages.put(session, new LinkedList<>());
        }
//        messages.get(session).add(message);
    }

}
