package ru.atom.dbhackaton.server.resource;

/**
 * Created by Юля on 29.03.2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.TokenDao;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;
import ru.atom.dbhackaton.server.service.Authorized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import ru.atom.resource.AllTokensHere;
//import ru.atom.resource.Authorized;
//import ru.atom.resource.HashCalculator;
//import ru.atom.resource.Token;
//import ru.atom.resource.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Path("/")
public class AuthResource {

    private static final Logger log = LogManager.getLogger(AuthResource.class);
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private static final TokenHolder allTokens = new TokenHolder();
    private static final ArrayList<String> names = new ArrayList<>();


    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("user") String name, @FormParam("password") String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            User user = new User(name, password);
            UserDao.getInstance().insert(session, user);
            txn.commit();
            return Response.ok().build();
        } catch (Exception e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User already exists.").build();
        }


    }


    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")

    public Response login(@FormParam("user") String name, @FormParam("password") String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            User user = UserDao.getInstance().getByName(session, name);
            if (user == null) {
                txn.rollback();
                log.info("no such user:" + name);
                return Response.status(Response.Status.FORBIDDEN).entity("No such user.").build();
            }
            if (!password.equals(user.getPass())) {
                txn.rollback();
                log.info(name + "enter wrong password");
                return Response.status(Response.Status.FORBIDDEN).entity("Wrong password.").build();
            }
            if (TokenDao.getInstance().getByUsername(session, name) != null) {
                txn.rollback();
                log.info(name + " already logged in");
                return Response.ok().entity(TokenDao.getInstance().getByUsername(session, name).toString()).build();
            }
            Token token = new Token().setUsername(name);
            TokenDao.getInstance().insert(session, token);
            log.info(name + "logged in");
            txn.commit();
            return Response.ok().entity(token.toString()).build();

        } catch (Exception e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception").build();
        }

    }


    @Authorized
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String strToken) {
        /*
        log.info("Logout this user ");
        Token logoutToken = new Token(text);
        if (allTokens.validateToken(logoutToken)) {
            allTokens.remove(logoutToken);
            return Response.ok("oooook (-_-(-_-)-_-)").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
        */
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            strToken = strToken.substring("Bearer".length()).trim();
            Token logoutToken = new Token(strToken);

            Token token = TokenDao.getInstance().getByToken(session, logoutToken.getToken());
            log.info("token:" + token);
            if (token == null) {
                txn.rollback();
                log.info(" not logged in");
                return Response.status(Response.Status.BAD_REQUEST).entity("Not logged in").build();
            }
            TokenDao.getInstance().remove(session, token);
            log.info(token.getUsername() + " logged out");
            txn.commit();
            return Response.ok().build();

        } catch (Exception e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception").build();
        }
    }


    @Path("/data/users")
    @GET
    public Response users() {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(allTokens.allUsersOnline());
        json = "{users:" + json + "}";
        log.info("GSON", json);
        return Response.ok(json).build();
    }


}
