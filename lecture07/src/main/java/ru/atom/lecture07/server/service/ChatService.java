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

import java.util.*;

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
                    .setTime(new Date())
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

    public void logout(String login) throws ChatException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) == null) {
                throw new ChatException("Not logined");
            }

            User logoutUser = UserDao.getInstance().getByName(session, login);

            Message logoutMessage = new Message()
                    .setUser(logoutUser)
                    .setTime(new Date())
                    .setValue("Logout");
            MessageDao.getInstance().insert(session, logoutMessage);

            UserDao.getInstance().delete(session, login);

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

            if (UserDao.getInstance().getByName(session, login) == null) {
                throw new ChatException("Not logined");
            }
            User messageUser = UserDao.getInstance().getByName(session, login);

            Message newMessage = new Message()
                    .setUser(messageUser)
                    .setValue(msg);
            MessageDao.getInstance().insert(session, newMessage);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public List<Message> viewChat(String login) throws ChatException {
        List<Message> listMessage;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) == null) {
                throw new ChatException("Not logined");
            }

            Integer user_id = UserDao.getInstance().getByName(session, login).getId();

            listMessage = MessageDao.getInstance().getById(session, user_id);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            listMessage = Collections.emptyList();
        }

        return listMessage;
    }
}
