package ru.atom.dbhackaton.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.LoginedUser;
import ru.atom.dbhackaton.server.model.User;
import java.util.List;
/**
 * Created by serega on 16.04.17.
 */
public class LoginedUserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static LoginedUserDao instance = new LoginedUserDao();

    public static LoginedUserDao getInstance() {
        return instance;
    }

    private LoginedUserDao() {
    }

    public List<LoginedUser> getAll(Session session) {
        return session.createCriteria(LoginedUser.class).list();
    }

    public void insert(Session session, LoginedUser user) {
        session.saveOrUpdate(user);
    }

    public LoginedUser getUser(Session session, User user) {
        return (LoginedUser) session
                .createQuery("from LoginedUser where reguser = :name")
                .setParameter("name", user)
                .uniqueResult();
    }

    public static LoginedUser getByToken(Session session, Long token) {
        return (LoginedUser) session
                .createQuery("from LoginedUser where token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }

    public void removeUser(Session session, LoginedUser user) {
        session.delete("loguser", user);
    }
}
