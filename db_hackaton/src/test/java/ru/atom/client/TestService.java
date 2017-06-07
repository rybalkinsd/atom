package ru.atom.client;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;

/**
 * Created by BBPax on 19.04.17.
 */
public class TestService {
    public User getUser(String login) {
        Transaction txn = null;
        User user = new User();
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            user = UserDao.getInstance().getByName(session, login);
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return user;
    }

    public Token getToken(long token) {
        Transaction txn = null;
        Token tok = new Token();
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            tok = TokenDao.getInstance().getToken(session, token);
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return tok;
    }
}
