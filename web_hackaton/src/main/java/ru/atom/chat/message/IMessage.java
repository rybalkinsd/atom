package ru.atom.chat.message;

import java.util.Date;

public interface IMessage {

    Date getDate();

    String getUserName();

    String getMessageBody();

    String getTime();
}
