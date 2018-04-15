package ru.atom.lecture08.websocket.dao;

import ru.atom.lecture08.websocket.model.Message;

import java.util.Date;
import java.util.List;

public interface MessageDao {

    void putMessage(String login, String msg, Date date);

    List<Message> getAll();

    Message getLast();
}
