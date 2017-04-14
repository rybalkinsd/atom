package ru.atom.dbhackaton.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.resource.User;

import java.util.List;

/**
 * Created by BBPax on 13.04.17.
 */
public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static UserDao instance = new UserDao();

    private UserDao(){}

    public static UserDao getInstance() {
        return instance;
    }

    public List<User> getAll(Session session) {
        return session.createCriteria(User.class).list();
    }

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }

    public User getByName(Session session, String login) {
        return (User) session
                .createQuery("from User where login = :login")
                .setParameter("login", login)
                .uniqueResult();
    }
}
