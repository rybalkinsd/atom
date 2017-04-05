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
                if (UserDao.getInstance().getByName(session, login).isLogined()) {
                    throw new ChatException("Already logined");
                } else {
                    User notNewUser = UserDao.getInstance()
                            .getByName(session, login)
                            .setLogined(true);

                    UserDao.getInstance().insert(session, notNewUser);

                    Message loginMessage = new Message()
                            .setUser(notNewUser)
                            .setValue("joined");
                    MessageDao.getInstance().insert(session, loginMessage);
                    txn.commit();
                    return;
                }

            }
            User newUser = new User().setLogin(login).setLogined(true);
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

            if (UserDao.getInstance().getByName(session, login) == null) {
                throw new ChatException("User is not existed");
            }
            if (!UserDao.getInstance().getByName(session, login).isLogined()) {
                throw new ChatException("User is not logined");
            }
            User newUser = UserDao.getInstance().getByName(session, login);
            Message userMessage = new Message()
                    .setUser(newUser)
                    .setValue(msg);
            MessageDao.getInstance().insert(session, userMessage);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public List<Message> viewChat() {
        List<Message> messageHistory;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            messageHistory = MessageDao.getInstance().getAll(session);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            messageHistory = Collections.emptyList();
        }
        return messageHistory;
    }

    public void logout(String login) throws ChatException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) == null) {
                throw new ChatException("Such user is not existed");
            }
            if (!UserDao.getInstance().getByName(session, login).isLogined()) {
                throw new ChatException("User is not logined");
            }
            User leaver = UserDao.getInstance().getByName(session, login).setLogined(false);

            Message logoutMessage = new Message()
                    .setUser(leaver)
                    .setValue("leaved");
            MessageDao.getInstance().insert(session, logoutMessage);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
