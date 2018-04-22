package ru.atom.chat.socket.topics;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum MailingType {
    TO_ALL,
    BACK_TO_SENDER,
    TO_GROUP,
    TO_USER;
    String recipient;

    @JsonIgnore
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
