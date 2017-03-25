package ru.atom.lecture06.server.dao;

import ru.atom.lecture06.server.model.Message;

import java.util.List;

/**
 * Created by sergey on 3/25/17.
 */
public class MessageDao implements Dao<Message> {
    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public List<Message> getAllWhere(String... conditions) {
        return null;
    }

    @Override
    public void insert(Message message) {

    }
}
