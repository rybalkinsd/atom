package ru.atom.lecture07.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.lecture07.server.dao.Database;
import ru.atom.lecture07.server.dao.MessageDao;
import ru.atom.lecture07.server.dao.UserDao;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;

/**
 * Service is responsible for using DAO and provide all the guaranties that are expected in resource method:
 * - transactions
 * - access rights check
 * - any other business guaranties
 */
public class ChatService {
    private static final Logger log = LogManager.getLogger(ChatService.class);

    public void login(String login) throws ChatException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new ChatException("Already logined");
            }
            User newUser = new User().setLogin(login);
            UserDao.getInstance().insert(session, newUser);

            Message loginMessage = new Message()
                    .setUser(newUser)
                    .setValue("joined");
            MessageDao.getInstance().insert(session, loginMessage);

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

    public void say(String login, String msg) throws ChatException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User user = UserDao.getByName(session, login);
            if (user == null) {
                throw new ChatException("Not logined!");
            }

            Message loginMessage = new Message()
                    .setUser(user)
                    .setValue(msg);
            MessageDao.getInstance().insert(session, loginMessage);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public List<Message> viewChat() {
        List<Message> allMessages;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            allMessages = MessageDao.getInstance().getAll(session);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            allMessages = Collections.emptyList();
        }
        return allMessages;
    }

    public void logout(String login) throws ChatException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User userToDelete = UserDao.getInstance().getByName(session, login);
            if (userToDelete == null) {
                throw new ChatException("Isn't logined");
            }

            Message logoutMessage = new Message()
                    .setUser(userToDelete)
                    .setValue("left");
            MessageDao.getInstance().insert(session, logoutMessage);

            UserDao.getInstance().dropUser(session, userToDelete);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
