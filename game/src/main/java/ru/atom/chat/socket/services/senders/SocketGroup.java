package ru.atom.chat.socket.services.senders;

public interface SocketGroup {
    void broadcast(String textMessage);

    //TODO MailType.TO_GROUP send messages in such Groups
}
