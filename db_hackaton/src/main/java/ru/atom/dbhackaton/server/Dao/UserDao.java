package ru.atom.dbhackaton.server.Dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.User;

public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);
    private static final UserDao userDao = new UserDao();

    private UserDao() {

    }

    public static UserDao getInstance() {
        return userDao;
    }

    public void register(Session session, User user) {
        session.persist(user);
    }

    public User getByName(Session session, String name) {
        return (User) session.createQuery("from User where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }
}
