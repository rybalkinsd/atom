package ru.atom.dbhackaton.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.resource.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by BBPax on 13.04.17.
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    public void register(String login, String password) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new AuthException("Already registrated");
            }
            User newUser = new User().setPassword(password).setLogin(login);
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    // TODO: 14.04.17  не реализован login
    public void login(String login, String password) throws AuthException {
        throw new NotImplementedException();
    }

    // TODO: 14.04.17  не реализован logout
    public void logout(Long Token) throws AuthException {
        throw new NotImplementedException();
    }
}
