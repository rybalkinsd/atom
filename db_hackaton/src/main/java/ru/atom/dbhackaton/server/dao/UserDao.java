package ru.atom.dbhackaton.server.dao;

/**
 * Created by Ксения on 12.04.2017.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.User;

import java.util.List;


public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    private UserDao(){}

    public List<User> getAll(Session session) {
        return session.createCriteria(User.class).list();
    }

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }

    public User getByName(Session session, String name) {
        return (User) session
                .createQuery("from registered_users where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }
}

