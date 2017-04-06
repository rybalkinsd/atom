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

import java.util.Collections;
import java.util.Date;
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

            if (UserDao.getInstance().getByName(session,"CONSOLE") == null)
                ChatService.createConsoleUser();
            User console = UserDao.getInstance().getByName(session,"CONSOLE");
            Message loginMessage = new Message()
                    .setUser(console)
                    .setValue("\"" + newUser.getLogin() + "\" joined");
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
            User user = UserDao.getInstance().getByName(session, login);
            if (user != null) {
                Message newMessage = new Message()
                        .setValue(msg)
                        .setUser(user)
                        .setTime(new Date());
                MessageDao.getInstance().insert(session, newMessage);
                txn.commit();
            } else throw new ChatException("Not logined");
        }
        catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public List<Message> viewChat() {
        List<Message> msg;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            msg = MessageDao.getInstance().getAll(session);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            msg = Collections.emptyList();
        }
        return msg;
    }

    public void logout(String login) throws ChatException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User logoutedUser = UserDao.getInstance().getByName(session, login);
            if (logoutedUser == null) {
                throw new ChatException("Such user is not logined");
            }
            if (UserDao.getInstance().getByName(session,"HISTORY") == null)
                ChatService.createHistoryUser();
            User historyUser = UserDao.getInstance().getByName(session,"HISTORY");
            List<Message> messages = MessageDao.getInstance().getByUser(session, logoutedUser);
            for (Message msg: messages) {
                msg.setValue("ex\"" + msg.getUser().getLogin() + "\" said:" + msg.getValue());
                msg.setUser(historyUser);
                MessageDao.getInstance().insert(session, msg);
            }
            Message logoutMessage = new Message()
                    .setTime(new Date())
                    .setUser(UserDao.getInstance().getByName(session, "CONSOLE"))
                    .setValue("\"" + logoutedUser.getLogin() + "\" leaved");
            MessageDao.getInstance().insert(session, logoutMessage);
            UserDao.getInstance().remove(session, logoutedUser);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public static void createHistoryUser() {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User historyUser = new User().setLogin("HISTORY");
            UserDao.getInstance().insert(session, historyUser);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public static void createConsoleUser() {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User deletedUser = new User().setLogin("CONSOLE");
            UserDao.getInstance().insert(session, deletedUser);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
