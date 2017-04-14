package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.base.Token;
import ru.atom.dbhackaton.server.base.User;
import ru.atom.dbhackaton.server.dao.TokenDao;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.storages.Database;

import javax.naming.AuthenticationException;
import javax.persistence.RollbackException;
import java.security.SecureRandom;

/**
 * Created by dmbragin on 4/12/17.
 */
public class AuthService {
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public void register(String login, String password) throws Exception {
        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new AuthenticationException("Пользователь " + login + " уже зарегистрирован");
            }

            User newUser = new User(login, getHash(password));
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (Exception ex) {
            logger.error("Registration error! {}", login);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public String generateToken() {
        final SecureRandom random = new SecureRandom();
        final long newValueToken = random.nextLong();
        return String.valueOf(newValueToken);
    }

    public String login(String login, String password) throws Exception {
        Transaction txn = null;
        Token token;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            User user = UserDao.getInstance().getUser(session, login, getHash(password));
            if (user == null) {
                throw new Authentication.Failed("");
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
            return "";
        }
        return token.getToken();

    }
    // TODO: 4/14/17  сделать метод для хеширования пароля
    public String getHash(String password) {
        return new String("123");
    }


}
