package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import ru.atom.dbhackaton.server.base.Token;
import ru.atom.dbhackaton.server.base.User;
import ru.atom.dbhackaton.server.dao.TokenDao;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.storages.Database;

import java.security.SecureRandom;

/**
 * Created by dmbragin on 4/12/17.
 */
public class AuthService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public void register(String login, String password) throws AuthException {
        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new AuthException("User \"" + login + "\" already registered");
            }

            User newUser = new User(login, getHash(password));
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException ex) {
            logger.error("Registration error! {}", login);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public static String registerCheck(String login) throws AuthException {
        Transaction txn = null;
        String isTrue = "False";
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) == null) {
                isTrue = "True";
            }
        } catch (RuntimeException ex) {
            logger.error("Registration error! {}", login);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return isTrue;
    }

    // TODO move to Token class
    public String generateToken() {
        final SecureRandom random = new SecureRandom();
        final long newValueToken = random.nextLong();
        return String.valueOf(newValueToken);
    }

    public String login(String login, String password) throws AuthException {
        Transaction txn = null;
        Token token;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            User user = UserDao.getInstance().getUser(session, login);
            if (user == null || !checkHash(user.getPassword(), password)) {
                throw new AuthException("Can not find user with this login and password");
            }

            token = TokenDao.getInstance().getTokenByUser(session, user);
            if (token == null) {
                String newValueToken = generateToken();
                // TODO: 4/14/17 проверить уникальность сгенерированного токена
//                boolean isChecked = TokenDao.getInstance().checkUnoqueToken(session, newValueToken);
                token = new Token(newValueToken, user);
                TokenDao.getInstance().insert(session, token);
            }
            txn.commit();
        } catch (RuntimeException e) {
            logger.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return null;
        }
        logger.info("Generate new token");
        return token.getToken();

    }

    public void logout(String tokenStr) {
        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            Token token = new Token();
            token.setToken(tokenStr);
            TokenDao.getInstance().delete(session, token);

            txn.commit();
        } catch (RuntimeException ex) {
            logger.error("Invalid token \"{}\", can not logout", tokenStr);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public boolean isAuth(String tokenStr) {
        Transaction txn = null;
        logger.info("Start check token \"{}\"", tokenStr);

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (!TokenDao.getInstance().isValidToken(session, tokenStr)) {
                return false;
            }

            txn.commit();
        } catch (RuntimeException ex) {
            logger.error("Invalid token \"{}\"", tokenStr);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return false;
        }
        return true;
    }

    public static String getHash(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkHash(String hashed, String password) {
        return BCrypt.checkpw(password, hashed);
    }

}
