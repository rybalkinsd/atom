package dbhackaton.dao;

/**
 * Created by ilnur on 12.04.17.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import dbhackaton.model.User;

import java.util.List;

public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    private UserDao() {
    }

    public List<User> getAll(Session session) {
        return session.createCriteria(User.class).list();
    }

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }


    public User getByName(Session session, String name) {
        User user = (User) session
                .createQuery("from User where name = :name")
                .setParameter("name", name)
                .uniqueResult();
        if (user != null) {
            return user;
        }
        return null;
    }
}

