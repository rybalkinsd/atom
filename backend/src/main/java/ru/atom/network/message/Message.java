package ru.atom.network.message;

/**
 * Created by sergey on 2/2/17.
 */
public class Message {
    private final String topic;
    private final String data;

    public Message(String topic, String data) {
        this.topic = topic;
        this.data = data;
    }

}
