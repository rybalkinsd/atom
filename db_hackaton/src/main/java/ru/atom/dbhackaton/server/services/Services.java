package ru.atom.dbhackaton.server.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.User;
import ru.atom.dbhackaton.exeptions.RegisterExeption;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.UserDao;

import static ru.atom.dbhackaton.MyLogger.getLog;
import static ru.atom.dbhackaton.WorkWithProperties.getStrBundle;

/**
 * Created by Western-Co on 12.04.2017.
 */
public class Services {
    public void registerUser(String name, String password) throws RegisterExeption {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, name) != null) {
                throw new RegisterExeption(getStrBundle().getString("already.registered"));
            }
            User newUser = new User(name, password, null);
            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            getLog().error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public String loginUser(String name, String password) throws RegisterExeption {
        Transaction txn = null;
        String token = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, name) == null) {
                throw new RegisterExeption(getStrBundle().getString("not.registered"));
            }

            User user = UserDao.getInstance().getByName(session, name);
            if (!user.getPassword().equals(String.valueOf(password.hashCode()))) {
                throw new RegisterExeption(getStrBundle().getString("login.error"));
            }
            user.setNewToken();
            token = String.valueOf(user.getToken());
            UserDao.getInstance().insert(session, user);

            txn.commit();
        } catch (RuntimeException e) {
            getLog().error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return token;
    }

    //Метод, позволяющий пользователю разлогиниться. Удаляет из базы данных присвоенный токен.
    public void logoutUser(String token) throws RegisterExeption {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            //Проверяем, есть ли такой пользователь в базе данных
            User logoutUser = UserDao.getInstance().getByToken(session, Long.parseLong(token));
            if (logoutUser == null) {
                // Если нет, то выдаем ошибку. Имя ошибки берется из файла resources/strong.properties
                throw new RegisterExeption(getStrBundle().getString("logout.error"));
            }
            // Set token to null
            logoutUser.setToken(null);
            // Update row in database
            UserDao.getInstance().insert(session, logoutUser);

            txn.commit();
        } catch (RuntimeException e) {
            getLog().error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
