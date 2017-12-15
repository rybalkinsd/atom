package ru.atom.lecture07.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.lecture07.server.model.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class MessageDao {
    private static final Logger log = LogManager.getLogger(MessageDao.class);

    private static MessageDao instance = new MessageDao();

    public static MessageDao getInstance() {
        return instance;
    }

    private MessageDao(){}

    public List<Message> getAll(Session session) {
        throw new NotImplementedException();
    }

    public void insert(Session session, Message message) {
        throw new NotImplementedException();
    }
}
