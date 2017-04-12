package ru.atom.dbhackaton.server;

import com.google.common.collect.RangeSet;
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
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.model.Token;
import ru.atom.dbhackaton.model.TokenStorage;
import ru.atom.dbhackaton.model.User;

import java.sql.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dmitriy on 26.03.17.
 */

@Path("/")
public class AuthResources {
    private static final Logger log = LogManager.getLogger(AuthResources.class);
    private static ConcurrentHashMap<String, User> logined = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, User> registered = new ConcurrentHashMap<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/register")
    public static Response register(@FormParam("user") String user, @FormParam("password") String password) {
        Transaction txn = null;
        try(Session session = Database.session()){
            txn = session.beginTransaction();
            if (UserDao.getInstance().getByName(session, user) != null) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
            }
            User newUser = new User(user, password);

            UserDao.getInstance().insert(session, newUser);

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception occured.").build();
        }
        registered.put(user, new User(user, password));
        log.info(user + " : registered");
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
            if (!loggingin.getPassword().equals(password)) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Wrong password").build();
            }
            Token token = new Token(user).setUser(loggingin);
            tokenValue = token.getToken();
            Token checked = TokenDao.getInstance().getByStrToken(session, token.getToken());
            if (checked == null) {
                txn.rollback();
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

        /*if (userObj.getPassword().equals(password)) {
            Token token = new Token(userObj);
            if (!(logined.containsKey(user))) {
                logined.put(user, userObj);
                TokenStorage.insert(token, userObj);
            }
            log.info(user + " : signed in");
            return Response.ok(token.toString()).build();
        }
        log.info(user + " has another password");
        return Response.status(Response.Status.BAD_REQUEST).entity("Not valid data").build();*/
        return Response.ok().entity(tokenValue).build();
    }

    @Authorized
    @POST
    @Produces("text/plain")
    @Path("/logout")
    public static Response logout(@HeaderParam("Authorization") String strToken) {
        strToken = strToken.replaceFirst("Bearer ", "");
        Token token = new Token(strToken);
        if (!TokenStorage.getInstance().containsKey(token)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Such user is not registered").build();
        }
        log.info("[" + TokenStorage.getByToken(token) + "]: logged out");
        logined.remove(TokenStorage.getByToken(token).getName());
        TokenStorage.remove(token);
        return Response.ok().build();
    }
}
