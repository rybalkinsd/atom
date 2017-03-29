package ru.atom.authserver;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.atom.entities.Token;
import ru.atom.entities.TokenContainer;
import ru.atom.entities.User;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

/**
 * Created by ikozin on 28.03.17.
 */
@Path("/")
public class AuthResource {
    private static final Logger log = LogManager.getLogger(AuthResource.class);

    private static final ArrayList<User> registered = new ArrayList<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/register")
    public static Response register(@FormParam("user") String user, @FormParam("password") String password) {
        if (registered.contains(new User(user, password))) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }
        log.info("[" + user + "] registered");
        registered.add(new User(user, password));
        return Response.ok("Successfully registered").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/login")
    public static Response login(@FormParam("user") String user, @FormParam("password") String password) {
        User processedUser = null;
        for (User lookedAtUser: registered) {
            if (lookedAtUser.getUsername().equals(user)) {
                processedUser = lookedAtUser;
                break;
            }
        }
        if (processedUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User " + user + " not registered").build();
        }
        if (!processedUser.getPassword().equals(password)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong password").build();
        }
        Token token = new Token(processedUser);
        if (!TokenContainer.containsUser(user)) {
            log.info("[" + user + "] logged in");
            TokenContainer.add(token, processedUser);
        }
        return Response.ok(token.toString()).build();
    }

    @Authorized
    @POST
    @Produces("text/plain")
    @Path("/logout")
    public static Response logout(@HeaderParam("Authorization") String tokenString) {
        Long tokenValue = Long.valueOf(tokenString.trim().substring(7));
        if (!TokenContainer.validate(tokenValue)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build();
        }
        log.info("[" + TokenContainer.get(tokenValue).getUsername() + "] logged out");
        TokenContainer.remove(tokenValue);
        return Response.ok("Successfully logged out").build();
    }
}
