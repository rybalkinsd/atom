package ru.atom.dbhackaton.server.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.User;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.exeptions.RegisterExeption;

import static ru.atom.dbhackaton.MyLogger.getLog;
import static ru.atom.dbhackaton.WorkWithProperties.getStrBundle;

public class ChatService {
    public void registerUser(String name, String password) throws RegisterExeption {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, name) != null) {
                throw new RegisterExeption(getStrBundle().getString("already.registered"));
            }
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
