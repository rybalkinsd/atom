package ru.atom.chat.message;

import ru.atom.chat.user.User;

import java.util.Date;

public interface IMessage {

    User getUser();

    String getValue();

    String getTime();
}
