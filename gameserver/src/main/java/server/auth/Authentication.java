package server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ConcurrentHashMap<String, String> registerData;
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

        if (registerData.putIfAbsent(user, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    static {
        registerData = new ConcurrentHashMap<>();
        tokens = new ConcurrentHashMap<>();
        tokensReversed = new ConcurrentHashMap<>();
        registerData.put("admin", "admin");
        tokens.put("admin", 1L);
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
            if (!authenticate(user, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            long token = issueToken(user);
            log.info("User '{}' successfully logged in", user);
            return Response.ok(Long.toString(token)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // curl -X POST
    //      -H "Authorization: Bearer {token}"
    //      -H "Host: localhost:8080
    // "http://localhost:8080/auth/logout"
    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response logoutUser(@HeaderParam("Authorization") String rawToken) {

        try {

            Long token = Long.parseLong(rawToken.substring("Bearer".length()).trim());
            System.out.println(token);

            if (!tokensReversed.containsKey(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                String user = tokensReversed.get(token);
                tokens.remove(user);
                tokensReversed.remove(token);
                if (log.isInfoEnabled()) {
                    log.info("User " + user + " logout");
                }
                return Response.ok("User: " + user + " logout successfully.").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    private boolean authenticate(String user, String password) throws Exception {
        return password.equals(registerData.get(user));
    }

    private Long issueToken(String user) {
        Long token = tokens.get(user);
        if (token != null) {
            return token;
        }
        token = ThreadLocalRandom.current().nextLong();
        log.info("Generate new token {} for user {}", token, user);
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
