package ru.atom.dbhackaton.server;

/**
 * Created by ilnur on 12.04.17.
 */


import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.Dao.Database;
import ru.atom.dbhackaton.server.Dao.UserDao;
import ru.atom.dbhackaton.server.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.login.LoginException;
import java.util.Collections;
import java.util.List;

public class Services {
    public void register(String login) throws LoginException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new LoginException("Already logined");
            }

            User newUser = new User().setLogin(login);
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

}
