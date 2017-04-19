package ru.atom.dbhackaton.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.UserDao;
import java.util.List;

/**
 * Created by BBPax on 13.04.17.
 */
public class UsersStorage extends AbstractStorage<String, User> {
    private static final Logger log = LogManager.getLogger(UsersStorage.class);

    public UsersStorage setUp() {
        Transaction txn = null;
        List<User> db;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            db = UserDao.getInstance().getAll(session);
            for (User i : db) {
                memory.put(i.getLogin(), i);
            }
            txn.commit();
        } catch (RuntimeException e) {
            log.info("DataBase download failed");
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return this;
    }
}
