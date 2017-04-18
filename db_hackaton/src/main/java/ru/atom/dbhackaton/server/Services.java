package ru.atom.dbhackaton.server;

/**
 * Created by ilnur on 12.04.17.
 */


import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.Dao.Database;
import ru.atom.dbhackaton.server.Dao.TokenDao;
import ru.atom.dbhackaton.server.Dao.UserDao;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;


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

    public void login(String login, String password) throws LoginException {

        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User user = UserDao.getInstance().getByName(session, login);  //kosyak

            log.info(user.getLogin());

            if (user.getLogin().equals(login)) {
                throw new LoginException("Not registered");
            } else if (user.getPassword().equals(password)) {
                throw new LoginException("Wrong password");
            } else if (user.getToken() != null) {
                throw new LoginException("Already logined");
            } else {
                user.setToken();
                UserDao.getInstance().insert(session, user);
                log.info("User " + login + "has been logined");
            }

            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
//
//        UserDao.getInstance().insert(session, newUser);
//
//
//
//        if (!userMap.authenticate(user, password)) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//
//        Token token = tokenMap.issueToken(user, tokenMap);
//
//        return Response.ok(Long.toString(token.getId())).build();


