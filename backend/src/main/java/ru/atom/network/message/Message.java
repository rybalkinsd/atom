package ru.atom.network.message;

import ru.atom.util.JsonHelper;

/**
 * Created by sergey on 2/2/17.
 */
public class Message<T> {
    private final Class<T> topic;
    private final String payload;

    public Message(Class<T> topic, String payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public T internalize() {
        return JsonHelper.fromJSON(payload, topic);
    }
}
