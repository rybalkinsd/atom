package dbhackaton;

/**
 * Created by ilnur on 12.04.17.
 */


import dbhackaton.dao.Database;
import dbhackaton.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import dbhackaton.model.User;

import javax.security.auth.login.LoginException;


public class Services {
    private static final Logger log = LogManager.getLogger(Services.class);

    public void register(String login, String password) throws LoginException {

        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new LoginException("Already logined");
            }

            User newUser = new User(login, password);    //тут id = null
            UserDao.getInstance().insert(session, newUser);
            log.info(login + " registered");
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public void login(String name, String password) throws LoginException {

        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User user = UserDao.getInstance().getByName(session, name);


            if (user == null) {
                throw new LoginException("Not registered");
            } else if (!user.getPassword().equals(password)) {
                throw new LoginException("Wrong password");
            } else if (user.getToken() != null) {
                throw new LoginException("Already logined");
            } else {
                user.setToken();
                UserDao.getInstance().insert(session, user);
                log.info("User " + name + "has been logined");
            }

            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public void logout(String name) throws LoginException {
        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User user = UserDao.getInstance().getByName(session, name);


            if (user == null) {
                throw new LoginException("Not registered");
            }  else if (user.getToken() == null) {
                throw new LoginException("User not logined");
            } else {
                user.getLogout();
                UserDao.getInstance().insert(session, user);
                log.info("User " + name + "has been logouted");
            }

            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public String hashPass(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Грустна");
        }
        return null;
    }
}
