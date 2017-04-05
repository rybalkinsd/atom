package ru.atom.lecture07.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.lecture07.server.model.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private static final Logger log = LogManager.getLogger(MessageDao.class);

    private static MessageDao instance = new MessageDao();

    public static MessageDao getInstance() {
        return instance;
    }

    private MessageDao(){}

    public static List<Message> getAll(Session session) {
        return session
                .createQuery("from Message ORDER BY time DESC")
                .list();
    }

    public void insert(Session session, Message message) {
        session.saveOrUpdate(message);
    }

    public List<Message> getByUser(Session session, UserDao user) {
        List<Message> messages = (List<Message>) session.createCriteria(user.getClass());
        return messages;
    }
}
