package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ru.atom.model.Token;
import ru.atom.model.TokenStorage;
import ru.atom.model.User;

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
    public static Response register(@QueryParam("user") String user, @QueryParam("password") String password) {
        if (registered.containsKey(user)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }
        registered.put(user, new User(user, password));
        log.info(user + " : registered");
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/login")
    public static Response login(@QueryParam("user") String user, @QueryParam("password") String password) {
        if (!registered.containsKey(user)) {
            log.info(user + " isn't registered");
            return Response.status(Response.Status.BAD_REQUEST).entity("Not registered").build();
        }
        User userObj = registered.get(user);
        if (userObj.getPassword().equals(password)) {
            Token token = new Token(userObj);
            if (!(logined.containsKey(user))) {
                logined.put(user, userObj);
                TokenStorage.insert(token, userObj);
            }
            log.info(user + " : signed in");
            return Response.ok(token.toString()).build();
        }
        log.info(user + " has another password");
        return Response.status(Response.Status.BAD_REQUEST).entity("Not valid data").build();
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
