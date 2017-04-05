package ru.atom.lecture07.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import java.util.List;

public class MessageDao {
    private static final Logger log = LogManager.getLogger(MessageDao.class);

    private static MessageDao instance = new MessageDao();

    public static MessageDao getInstance() {
        return instance;
    }

    private MessageDao(){}

    public List<Message> getAll(Session session) {
        return session.createCriteria(Message.class).addOrder(Order.asc("time")).list();
    }

    public List<Message> getByUser(Session session, User user) {
        return session.createQuery("from Message where user_id = :user_id")
                .setParameter("user_id", user.getId())
                .list();
    }

    public void insert(Session session, Message message) {
        session.saveOrUpdate(message);
    }

    public void remove(Session session, Message message) {
        session.delete(message);
    }
}
