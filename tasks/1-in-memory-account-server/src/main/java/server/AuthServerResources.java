package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import resources.User;
import token.Token;
import token.TokenManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;

/**
 * Created by Robin on 28.03.2017.
 */
@Path("/auth")
public class AuthServerResources {
    private static final Logger log = LogManager.getLogger(AuthServerResources.class);

    public static final ConcurrentArrayQueue<User> registeredUsers = new ConcurrentArrayQueue<>();
    public static final TokenManager loggedUsers = new TokenManager();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("user") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null) {
            log.info("Registration try with null parameters name:" + name + " password: " + password);
            return Response.status(Response.Status.BAD_REQUEST).entity("Name or password couldn't be empty").build();
        }
        if (name.length() > 30) {
            log.info("Registration try with long name:" + name + " password: " + password);
            return Response.status(Response.Status.BAD_REQUEST).entity("Name is too long").build();

        }

        User registeredUser = new User(name, password);

        if (registeredUsers.contains(registeredUser)) {
            log.info("Registration try with registered user:" + name + " password: " + password);
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }

        log.info("[" + registeredUser + "] registered");
        registeredUsers.add(registeredUser);

        return Response.ok("New user registered").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@FormParam("user") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null) {
            log.info("logged-in with null parameters name:" + name + " password: " + password);
            return Response.status(Response.Status.BAD_REQUEST).entity("Name or password couldn't be empty").build();
        }
        if (name.length() > 30) {
            log.info("logged-in with long name:" + name + " password: " + password);
            return Response.status(Response.Status.BAD_REQUEST).entity("Name is too long").build();

        }

        User loggedUser = new User(name, password);

        if (!registeredUsers.contains(loggedUser)) {
            log.info("logged-in with unregistered user name:" + name + " password: " + password);
            return Response.status(Response.Status.BAD_REQUEST).entity("This user is not registered").build();
        }

        Token token = new Token(loggedUser);
        if (loggedUsers.containsKey(token)) {
            log.info("user: [" + loggedUser + "] already logged-in (token: " + token + ")");
        } else {
            loggedUsers.put(token, loggedUser);
            log.info("[" + loggedUser + "] logged-in with token " + token);
        }

        return Response.ok("user logged-in with token " + token).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenStr) {
        Token token = new Token(tokenStr);
        loggedUsers.remove(token);
        log.info("log out with token: " + token);
        return Response.ok("log out with token: " + token).build();
    }
}
