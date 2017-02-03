package ru.atom.network.message;

/**
 * Created by sergey on 2/2/17.
 */
public class Message {
    private final Topic topic;
    private final String data;

    public Message(Topic topic, String data) {
        this.topic = topic;
        this.data = data;
    }

    Topic getTopic() {
        return topic;
    }

    String getData() {
        return data;
    }
}
