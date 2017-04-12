package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Created by konstantin on 12.04.17.
 */
public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    private UserDao(){}

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }
}
