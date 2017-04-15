package ru.atom.dbhackaton.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;

/**
 * Created by BBPax on 13.04.17.
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    public void register(User newUser) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            if (UserDao.getInstance().getByName(session, newUser.getLogin()) != null) {
                throw new AuthException("Already registrated");
            }
            UserDao.getInstance().insert(session, newUser);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    // TODO: 14.04.17  не обрабатываются запросы, в которых юзера не существует(это ловится еще в AuthResource)
    public void login(Token token) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            TokenDao.getInstance().insert(session, token);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    // TODO: 14.04.17  возможное отсутствие токенов в БД ловится еще в AuthResource
    public void logout(Token token) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            TokenDao.getInstance().delete(session, token);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
