package ru.atom.dbhackaton.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.User;
import ru.atom.dbhackaton.server.db.Database;

/**
 * Created by konstantin on 12.04.17.
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    public void login(String login) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User newUser = new User().setLogin(login);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
