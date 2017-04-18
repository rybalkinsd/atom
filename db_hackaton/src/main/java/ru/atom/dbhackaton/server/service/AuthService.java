package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.User;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Service is responsible for using DAO and provide all the guarantees that are expected in resource method:
 * - transactions
 * - access rights check
 * - any other business guarantees
 */
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    public void login(String login) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new AuthException("Already logined");
            }
            //User newUser = new User().setLogin(login);
            //UserDao.getInstance().insert(session, newUser);

            //Message loginMessage = new Message()
            //       .setUser(newUser)
            //        .setValue("joined");
            //MessageDao.getInstance().insert(session, loginMessage);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public List<User> getOnline() {
        List<User> online;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            online = UserDao.getInstance().getAll(session);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            online = Collections.emptyList();
        }
        return online;
    }

    public void say(String login, String msg) throws AuthException {
        Transaction txn = null;
        try (Session session = Database.session()) {
        	txn = session.beginTransaction();
        	
            //User newUser = new User().setLogin(login);
            User sayUser = UserDao.getInstance().getByName(session, login);
            if (sayUser == null) {
                throw new AuthException("Must log in first");
            }
            
            LocalDateTime ldt = LocalDateTime.now();
            
            Date date = new Date(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth());

            /*
            Message chatMsg = new Message()
                    .setUser(sayUser)
                    .setValue(msg)
            		.setTime(date);
            MessageDao.getInstance().insert(session, chatMsg);
            */
            //messages = MessageDao.getInstance().

            txn.commit();
        	
        } catch (RuntimeException e) {
        	log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
