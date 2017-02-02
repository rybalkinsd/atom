package ru.atom.network.message;

/**
 * Created by sergey on 2/2/17.
 */
public class Message {
    private final Class<?> topic;
    private final String data;

    public Message(Class<?> topic, String data) {
        this.topic = topic;
        this.data = data;
    }

//    public <T> T internalize() {
//        return JsonHelper.fromJSON(data, topic);
//    }



}
