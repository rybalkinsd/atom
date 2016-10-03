package ru.atom.server.auth;

/**
 * Created by s.rybalkin on 28.09.2016.
 */

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class Authentication {
    private static ConcurrentHashMap<String, String> credentials;
    private static ConcurrentHashMap<String, Long> tokens;

    static {
        credentials = new ConcurrentHashMap<>();
        credentials.put("admin", "admin");
        tokens = new ConcurrentHashMap<>();
        tokens.put("admin", 1L);
    }

    // curl -H 'Authorization: Bearer 2133e36c-8f31-455f-840e-1e034d4975fd' http://localhost:8080/auth/dummy
    //@Authorized
    @GET
    @Path("dummy")
    public Response dummy() {
        return Response.ok().build();
    }

    // curl -X POST -H "Content-Type: application/x-www-form-urlencoded"
    //              -H "Host: localhost:8080"
    //              -H "X-Amz-Date: 20161003T134606Z"
    //              -H "Cache-Control: no-cache"
    //              -d 'login=admin&password=admin'
    // "http://localhost:8080/auth/login"
    @POST
    @Path("register")
    public Response register(@FormParam("login") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (credentials.putIfAbsent(user, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("login")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("login") String login,
                                     @FormParam("password") String password) {

        try {
            // Authenticate the user using the credentials provided
            if (!authenticate(login, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user
            long token = issueToken(login);

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
        return token;
    }

    public static void validateToken(String token) throws Exception {
        Long parsedToken = Long.parseLong(token);
        if (!tokens.containsValue(parsedToken)) {
            throw new Exception("Token validation exception");
        }
    }
}
