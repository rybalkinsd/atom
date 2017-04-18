package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;

import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.LoginedUserDao;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.LoginedUser;
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

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new AuthException("Already registered");
            }
            User newUser = new User()
                    .setLogin(login)
//                    .setPassword(password)
                    .setTime(new Date())
                    .setHashCode(password);
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public static void login(String login, String password) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User newUser = new User()
                    .setLogin(login)
                    .setHashCode(password);
            User user = UserDao.getInstance().getByHashCode(session, newUser.getHashCode());
            if (user == null) {
                throw new AuthException("Not registered");
            } else if (LoginedUserDao.getInstance().getUser(session, user) != null)
                throw new AuthException("Already logined");
            else {

                LoginedUser newLoginedUser = new LoginedUser()
                        .setUser(user)
                        .setTime(new Date());

                LoginedUserDao.getInstance().insert(session, newLoginedUser);

                txn.commit();
            }
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public static void logout(Long token) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            LoginedUser removeUser = LoginedUserDao.getInstance().getByToken(session, token);
            if (removeUser.getToken() == null) {
                throw new AuthException("Not logined");
            }

            LoginedUserDao.getInstance().removeUser(session, removeUser);
            log.info("[" + removeUser.getUser().toString() + "]: logged out");
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
