package ru.atom.dbhackaton.auth.server;

/**
 * Created by ilnur on 12.04.17.
 */


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.auth.dao.Database;
import ru.atom.dbhackaton.auth.dao.UserDao;
import ru.atom.dbhackaton.auth.model.User;

import javax.ws.rs.core.Response;


public class Services {
    private static final Logger log = LogManager.getLogger(Services.class);

    public Response register(String login, String password) {

        if (login.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too short name, sorry :(")
                    .build();
        }
        if (login.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too long name, sorry :(")
                    .build();
        }
        if (password.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too short pass, sorry :(")
                    .build();
        }
        if (password.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too long pass, sorry :(")
                    .build();
        }

        String hashPassword = hashPass(password);

        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Already registered")
                        .build();
            }

            User newUser = new User(login, hashPassword);    //тут id = null
            UserDao.getInstance().insert(session, newUser);
            log.info(login + " registered");
            txn.commit();
            return Response.ok().build();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response login(String name, String password) {
        String hashPassword = hashPass(password);

        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User user = UserDao.getInstance().getByName(session, name);

            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Not registered")
                        .build();
            } else if (!user.getPassword().equals(hashPassword)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Wrong password")
                        .build();
            } else if (user.getToken() != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Already logined")
                        .build();
            } else {
                user.setToken();
                UserDao.getInstance().insert(session, user);
                log.info("User " + name + "has been logined");
            }

            txn.commit();

            return Response.ok().entity(user.getToken()).build();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public Response logout(String tokenParam) {
        String token = tokenParam.substring("Bearer".length()).trim();

        Transaction txn = null;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            User user = UserDao.getInstance().getByToken(session, token);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Not logined").build();
            }
            user.getLogout();
            UserDao.getInstance().insert(session, user);
            log.info("User " + user.getName() + "has been logouted");

            txn.commit();

            return Response.ok().build();
        } catch (Exception e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    public String hashPass(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            log.info("Illnur's error");
        }
        return null;
    }
}
