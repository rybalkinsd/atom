package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.http.server.dao.Database;
import ru.atom.http.server.dao.UserDao;
import ru.atom.http.server.model.Token;
import ru.atom.http.server.model.TokenStorage;
import ru.atom.http.server.model.User;
import ru.atom.http.server.model.UsersStorage;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 * Created by zarina on 23.03.17.
 */
@Path("/auth/")
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);
    protected static final UsersStorage users = new UsersStorage();
    protected static final TokenStorage tokens = new TokenStorage();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("user") String name, @FormParam("password") String password)
            throws Exception {
        Response response;
        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            log.info("Params empty");
            response =  Response.status(Response.Status.BAD_REQUEST).build();
        }

        log.info("Register user " + name);
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, name) != null) {
                log.info("User exist " + name);
                response = Response.status(Response.Status.FORBIDDEN).build();
            } else {
                log.info("New user " + name);
                User newUser = new User().setName(name).setPassword(password);
                UserDao.getInstance().insert(session, newUser);
                response = Response.ok("ok").build();
            }

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            response = Response.status(Response.Status.BAD_GATEWAY).build();
        }
        return response;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("user") String name, @FormParam("password") String password)
            throws Exception {
        if (name == null || name.isEmpty() || password == null || password.isEmpty()) {
            log.info("Params empty");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        log.info("Login user " + name);
        User user = users.get(name);
        if (user == null) {
            log.info("User " + name + " not found");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!user.validPassword(password)) {
            log.info("Invalid password for user " + name);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Long tokenNum = tokens.getToken(user);
        if (tokenNum == null) {
            log.info("Generate token");
            Token token = new Token(user);
            tokenNum = token.getToken();
            tokens.put(tokenNum, token);
        }
        return  Response.ok(tokenNum.toString()).build();
    }

    @POST
    @Path("/changePassword")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response changePassword(@FormParam("user") String name, @FormParam("password") String oldPassword,
                                   @FormParam("new_password") String newPassword)
            throws Exception {
        if (name == null || name.isEmpty() || oldPassword == null || oldPassword.isEmpty()
                || newPassword == null || newPassword.isEmpty()) {
            log.info("Params empty");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        log.info("Change password for user " + name);
        User user = users.get(name);
        if (user == null) {
            log.info("User " + name + " not found");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!user.validPassword(oldPassword)) {
            log.info("Invalid password for user " + name);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (user.changePassword(oldPassword, newPassword)) {
            log.info("New password for user " + name + " saved");
            return  Response.ok("ok").build();
        }
        log.info("New password for user not " + name + " saved");
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Authorized
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) {
        Long token = Long.parseLong(tokenParam.substring("Bearer".length()).trim());
        log.info("Logout with token " + token);
        tokens.remove(token);
        return  Response.ok("ok").build();
    }

    public static LinkedList<User> getAllUsers() {
        log.info("All users " + users);
        return users.getAll();
    }

    public static LinkedList<User> getOnlineUsers() {
        LinkedList<User> users = new LinkedList<>();
        for (Token token : tokens.getAll()) {
            users.add(token.getUser());
        }
        log.info("Online users " + users);
        return users;
    }

    public static boolean validToken(String token) {
        log.info("Valid token " + token);
        return tokens.validToken(token);
    }
}
