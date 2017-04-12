package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.base.User;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.storages.Database;

/**
 * Created by dmbragin on 4/12/17.
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    public void register(String login, String passwd) throws Exception {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new Exception("Already logined");
            }
            User newUser = new User(login, passwd);
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
