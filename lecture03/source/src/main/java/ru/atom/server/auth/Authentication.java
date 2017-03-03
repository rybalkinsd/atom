package ru.atom.server.auth;

/**
 * Created by s.rybalkin on 28.09.2016.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ConcurrentHashMap<String, String> credentials;
    private static ConcurrentHashMap<String, Long> tokens;
    private static ConcurrentHashMap<Long, String> tokensReversed;

    // curl -i
    //      -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: {IP}:8080"
    //      -d "login={}&password={}"
    // "{IP}:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (credentials.putIfAbsent(user, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    static {
        credentials = new ConcurrentHashMap<>();
        credentials.put("admin", "admin");
        tokens = new ConcurrentHashMap<>();
        tokens.put("admin", 1L);
        tokensReversed = new ConcurrentHashMap<>();
        tokensReversed.put(1L, "admin");
    }

    // curl -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: localhost:8080"
    //      -d "login=admin&password=admin"
    // "http://localhost:8080/auth/login"
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("login") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            if (!authenticate(user, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user
            long token = issueToken(user);
            log.info("User '{}' logged in", user);

            // Return the token on the response
            return Response.ok(Long.toString(token)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String user, String password) throws Exception {
        return password.equals(credentials.get(user));
    }

    private Long issueToken(String user) {
        Long token = tokens.get(user);
        if (token != null) {
            return token;
        }

        token = ThreadLocalRandom.current().nextLong();
        tokens.put(user, token);
        tokensReversed.put(token, user);
        return token;
    }

    static void validateToken(String rawToken) throws Exception {
        Long token = Long.parseLong(rawToken);
        if (!tokensReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokensReversed.get(token));
    }
}
