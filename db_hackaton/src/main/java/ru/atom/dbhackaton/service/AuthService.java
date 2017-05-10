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

    public Token login(String login, String pass) throws AuthException {
        Transaction txn = null;
        User user;
        Token token = new Token();
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            user = UserDao.getInstance().getByName(session, login);
            if (user == null) {
                throw new AuthException("Not existed");
            }
            if (!user.validPassword(pass)) {
                return null;
            }
            if (TokenDao.getInstance().getByUser(session, user) == null) {
                token.setUser(user).setToken(0L);
                TokenDao.getInstance().insert(session, token);
            } else {
                token = TokenDao.getInstance().getByUser(session, user);
            }
            txn.commit();

        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return token;
    }

    public void logout(Long token) throws AuthException {
        Transaction txn = null;
        Token tok;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            tok = TokenDao.getInstance().getToken(session, token);
            if (tok == null) {
                throw new AuthException("Not authorized");
            }
            TokenDao.getInstance().delete(session, tok);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
