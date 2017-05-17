package ru.atom.bombergirl.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.TokenDao;
import ru.atom.bombergirl.dao.UserDao;
import ru.atom.bombergirl.dbmodel.Token;
import ru.atom.bombergirl.dbmodel.User;

/**
 * Created by dmitriy on 26.03.17.
 */

@Path("/")
public class AuthResources {
    private static final Logger log = LogManager.getLogger(AuthResources.class);

    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static int workload = 12;

    public static String hashPassword(String passwordPlaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashedPassword = BCrypt.hashpw(passwordPlaintext, salt);

        return hashedPassword;
    }

    public static boolean checkPassword(String passwordPlaintext, String storedHash) {
        boolean passwordVerified = false;

        if (null == storedHash || !storedHash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        passwordVerified = BCrypt.checkpw(passwordPlaintext, storedHash);

        return passwordVerified;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/register")
    public static Response register(@FormParam("user") String user, @FormParam("password") String password) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            if (UserDao.getInstance().getByName(session, user) != null) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
            }
            User newUser = new User(user, hashPassword(password));

            UserDao.getInstance().insert(session, newUser);
            log.info(user + " : registered");

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception occured.").build();
        }
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/login")
    public static Response login(@FormParam("user") String user, @FormParam("password") String password) {
        String tokenValue;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            User loggingin = UserDao.getInstance().getByName(session, user);
            if (loggingin == null) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Not registered").build();
            }
            if (!checkPassword(password, loggingin.getPassword())) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Wrong password").build();
            }
            Token token = new Token(loggingin).setUser(loggingin);
            tokenValue = token.getToken();
            Token checked = TokenDao.getInstance().getByStrToken(session, token.getToken());
            if (checked != null) {
                txn.rollback();
                log.info("Token was already given");
                return Response.ok().entity(token.getToken()).build();
            }
            TokenDao.getInstance().insert(session, token);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception occured.").build();
        }
        log.info(user + " received token");
        return Response.ok().entity(tokenValue).build();
    }

    @Authorized
    @POST
    @Produces("text/plain")
    @Path("/logout")
    public static Response logout(@HeaderParam("Authorization") String strToken) {
        strToken = strToken.replaceFirst("Bearer ", "");
        Token token;
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            token = TokenDao.getInstance().getByStrToken(session, strToken);
            if (token == null) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Not logined").build();
            }
            TokenDao.getInstance().remove(session, token);
            log.info("[" + token.getUser().getName() + "]: logged out");
            return Response.ok().build();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception occured.").build();
        }
    }
}
