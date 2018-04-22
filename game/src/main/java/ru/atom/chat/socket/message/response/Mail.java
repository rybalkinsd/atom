package ru.atom.chat.socket.message.response;

import ru.atom.chat.socket.topics.MailingType;

public class Mail {
    private final MailingType type;
    private final String data;


    public Mail(MailingType type, String data) {
        this.type = type;
        this.data = data;
    }


    public String getData() {
        return data;
    }

    public MailingType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{mailingType:" + type + ",data:" + data + "}";
    }
}
