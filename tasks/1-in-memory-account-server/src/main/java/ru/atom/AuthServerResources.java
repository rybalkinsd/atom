package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Iterator;

/**
 * Created by Vlad on 26.03.2017.
 */
@Path("/auth")
public class AuthServerResources {
    private static final Logger log = LogManager.getLogger(AuthServerResources.class);

    private static final ConcurrentArrayQueue<User> users = new ConcurrentArrayQueue<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("user") String user, @FormParam("password") String password) {
        User tmp = new User(user, password);

        if (user == null || password == null) {
            log.info("Empty name or password");
            return Response.status(Response.Status.BAD_REQUEST).entity("Empty name or password").build();
        }

        if (user.length() > 20) {
            log.info("User's name too long");
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name (more than 20 symbols)").build();
        }

        if (user.length() < 3) {
            log.info("User's name too short");
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name (less than 3 symbols)").build();
        }

        if (password.length() < 8) {
            log.info("User's password too short");
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short password (less than 8 symbols)").build();
        }

        if (users.contains(tmp)) {
            log.info("User exist " + tmp.getName());
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }

        log.info("New user registered\n" +
                "--- user: " + user + "\n" +
                "--- password: " + password);
        users.add(tmp);
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@FormParam("user") String user, @FormParam("password") String password) {
        User tmp = new User(user, password);

        if (user == null || password == null) {
            log.info("Empty name or password");
            return Response.status(Response.Status.BAD_REQUEST).entity("Empty name or password").build();
        }

        if (!users.contains(tmp)) {
            log.info("User not registered");
            return Response.status(Response.Status.BAD_REQUEST).entity("User not registered").build();
        } else {
            if (validatePassword(tmp)) {
                Token token = new Token(tmp);
                if (!TokensStorage.validateToken(token)) {
                    log.info("Generate token for user: " + user + " --- " + token.toString());
                    TokensStorage.addToken(token);
                    return Response.ok(token.toString()).build();
                }
                log.info("User " + user + " already has token");
                return Response.ok(token.toString()).build();
            } else {
                log.info("Invalid password");
                return Response.status(Response.Status.FORBIDDEN).entity("Invalid password").build();
            }
        }
    }

    @Authorized
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) {
        Long token = Long.parseLong(tokenParam.substring("Bearer".length()).trim());
        log.info("Logout with token " + token);
        TokensStorage.removeToken(token);
        return  Response.ok().build();
    }

    private boolean validatePassword(User user) {
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            if (user.fullEquals(it.next()))
                return true;
        }
        return false;
    }

}
