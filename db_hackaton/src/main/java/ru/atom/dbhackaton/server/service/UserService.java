package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.TokenDao;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;

import java.util.Date;
import java.util.Random;

/**
 * Created by pavel on 12.04.17.
 */
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    public void register(String login, String password) throws UserException {
        Transaction tnx = null;
        try (Session session = Database.session()) {
            tnx = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new UserException("Already registered");
            }

            User newUser = new User();
            newUser.setName(login);
            newUser.setPasswordHash(password.hashCode());
            newUser.setRegisterDate(new Date());
            UserDao.getInstance().register(session, newUser);

            tnx.commit();
            LOGGER.info("User {} registered!", newUser.getName());
        } catch (RuntimeException e) {
            if (tnx != null && tnx.isActive()) {
                tnx.rollback();
                LOGGER.error("Transaction failed");
            }
        }
    }

    public long login(String login, String password) throws UserException {
        Transaction tnx = null;
        try (Session session = Database.session()) {
            tnx = session.beginTransaction();

            User registeredUser = UserDao.getInstance().getByName(session, login);
            if (registeredUser == null) {
                throw new UserException("User not register");
            } else if (!registeredUser.getPasswordHash().equals(password.hashCode())) {
                throw new UserException("Wrong password");
            }
            Token exist = TokenDao.getInstance().getTokenByUserName(session, login);
            if (exist != null) {
                return exist.getToken();
            }
            long numToken = new Random().nextLong();
            Token token = new Token();
            token.setUser(registeredUser);
            token.setToken(numToken);

            TokenDao.getInstance().login(session, token);
            tnx.commit();
            LOGGER.info("User {} is loggined!", registeredUser.getName());
            return numToken;
        } catch (RuntimeException e) {
            if (tnx != null && tnx.isActive()) {
                tnx.rollback();
            }
            LOGGER.error("Transaction failed!");
            throw new RuntimeException();
        }
    }

    public void logout(long token) throws UserException {
        Transaction tnx = null;
        try (Session session = Database.session()) {
            tnx = session.beginTransaction();
            TokenDao.getInstance().logout(session,token);
            tnx.commit();
            LOGGER.info("User with token {} is loggined out", token);
        } catch (RuntimeException e) {
            if (tnx != null && tnx.isActive()) {
                tnx.rollback();
                LOGGER.error("Transaction failed!");
            }
        }
    }
}
