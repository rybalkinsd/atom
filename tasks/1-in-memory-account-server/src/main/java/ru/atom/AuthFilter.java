package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by ilysk on 26.03.17.
 */
@Path("/")
public class AuthFilter {
    private static final Logger log = LogManager.getLogger(AuthFilter.class);
    private static final ConcurrentArrayQueue<User> registered = new ConcurrentArrayQueue<>();
    private static final TokenConcurrentHashMap<Token, User> logined = new TokenConcurrentHashMap<>();

    @POST
    @Path("auth/register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response registration(@FormParam("user") String user,
                                 @FormParam("password") String password) {

        if (user.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Your login length is bigger than 30.")
                    .build();
        }
        if (registered.contains(new User(user, password))) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User with this name has already registered.")
                    .build();
        }

        registered.add(new User(user, password));
        log.info("[" + user + "] registered");

        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/login")
    public Response authentication(@FormParam("user") String user,
                                   @FormParam("password") String password) {
        if (!registered.contains(new User(user, password))) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("You aren't registered.")
                    .build();
        }

        if (logined.containsValue(new User(user, password))) {
            for (Object token: logined.keySet()) {
                if (logined.get(token).equals(new User(user, password))) {
                    return Response.ok(token.toString()).build();
                }
            }
        }

        Token token = new Token();
        logined.put(token, new User(user, password));
        log.info("[" + user + "] logined");

        return Response.ok(token.toString()).build();
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/logout")
    public Response logout(ContainerRequestContext requestContext) {
        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No token in request header.")
                    .build();
        }

        // Extract the token from the HTTP Authorization header
        String tokenString = authorizationHeader.substring("Bearer".length()).trim();
        Token token = new Token(tokenString);

        if (!logined.containsKey(token)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("You aren't logined.")
                    .build();
        }

        String userToLogoutString = logined.get(token).toString();

        logined.remove(token);
        log.info("[" + userToLogoutString + "] logout");

        return Response.ok("Succeed logout.").build();
    }

    @GET
    @Path("data/users")
    @Produces("application/json")
    public Response getUsers() {
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode usersArray = mapper.createArrayNode();

        for (User user : logined.getAllUsers()) {
            usersArray.add(user.getLogin());
        }

        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.putPOJO("users", usersArray);

        return Response.ok().entity(jsonNode.toString()).build();
    }
}
