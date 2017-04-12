package ru.atom.dbhackaton.server.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.User;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.UserDao;

import static ru.atom.dbhackaton.MyLogger.getLog;

/**
 * Created by Western-Co on 12.04.2017.
 */
public class Services {
    public void registerUser(String name, String password) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            System.out.println("Hello");
            txn = session.beginTransaction();

//            if (UserDao.getInstance().getByName(session, name) != null) {
////                throw new ChatException("Already logined");
//            }
            User newUser = new User(name, password);
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            getLog().error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
