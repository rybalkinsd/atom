package ru.atom.chat.dao;


import ru.atom.chat.message.Message;

import java.util.List;

public interface MessageDao {

    void save(Message message);

    List<Message> findAll();
}
