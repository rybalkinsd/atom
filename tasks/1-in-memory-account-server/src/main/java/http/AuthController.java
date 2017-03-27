package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.User;
import tokens.Authorized;
import tokens.Token;
import tokens.TokenManager;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.GET;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kinetik on 26.03.17.
 */
@Path("/")
public class AuthController {
    private static final Logger log = LogManager.getLogger(AuthController.class);

    private static final ConcurrentHashMap<String, User> registared = new ConcurrentHashMap<>();
    private final TokenManager tokenManager = new TokenManager();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/registrate")
    public Response registrate(@FormParam("login") String name, @FormParam("password") String password) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please enter your name").build();
        }
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (registared.containsKey(name.trim())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("This name is registrated").build();
        }
        if (name.length() < 3) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name").build();
        }
        log.info("[" + name + "] registrated");
        registared.put(name, new User(name.trim(), password.trim()));
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/login")
    public Response login(@FormParam("login") String name, @FormParam("password") String password) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad username").build();
        }
        if (!registared.containsKey(name.trim())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User isn't registrated").build();
        }
        User user = registared.get(name.trim());
        if (!user.getPassword().equals(password.trim())) {
            return Response.status(Response.Status.FORBIDDEN).entity("Bad password").build();
        }
        if (user.getToken() != null) {
            log.info("[" + name + "] get token one time more" + user.getToken());
            return Response.ok(user.getToken().getValue()).build();
        } else {
            Token tokenValue = tokenManager.getNewToken(user);
            user.setToken(tokenValue);
            log.info("[" + name + "] get token " + tokenValue);
            return Response.ok(user.getToken().getValue()).build();
        }
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenIn) {
        Long token = Long.parseLong(tokenIn.substring("Bearer".length()).trim());
        User user = registared.get(tokenManager.logout(token));
        log.info("[" + user.getName() + "] logout");
        user.setToken(null);
        return Response.ok().build();
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("/getUsers")
    public Response getUsers(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenIn) {
        try {
            Long token = Long.parseLong(tokenIn.substring("Bearer".length()).trim());
            log.info("[" + tokenManager.getUserByToken(token).getName() + "] gets users");
            return Response.ok(tokenManager.getLoginUsers()).build();
        } catch (Exception e) {
            log.error(e.getCause() + " " + e.getStackTrace());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Something went wrong").build();
        }
    }
}
