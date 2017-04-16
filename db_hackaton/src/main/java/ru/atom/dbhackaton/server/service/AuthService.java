package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.User;

/**
 * Created by konstantin on 12.04.17.
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    public static void register(String login, String password) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User newUser = new User()
                    .setLogin(login)
                    .setPassword(password);
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
