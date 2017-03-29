package ru.atom.auth.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class AuthenticationServlet {
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static ConcurrentHashMap<User, String> credentials = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<User, Token> tokens = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Token, User> tokensReversed = new ConcurrentHashMap<>();

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("name") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = new User(name);

        if (credentials.putIfAbsent(user, password) != null) {
            log.info("Attempt for registering dublicate user: " + user.getName());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user with name " + user.getName() + " registered");
        return Response.ok("User " + user.getName() + " registered.").build();
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("name") String name,
                          @FormParam("password") String password) {

        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = new User(name);

        try {
            if (!authenticate(user, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Token token = issueToken(user);

            return Response.ok(Long.toString(token.getId())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Authorized
    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logout() {
        Token token = AuthenticationFilter.getToken();

        try {
            log.info("User " + tokensReversed.get(token).getName() + " logout" );
            tokens.remove(tokensReversed.get(token));
            tokensReversed.remove(token);
            return Response.ok("You have logout").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(User user, String password) throws Exception {
        return password.equals(credentials.get(user));
    }

    private Token issueToken(User user) {
        Token token = tokens.get(user);
        if (token != null) {
            log.info("User " + user.getName() + " logged already");
            return token;
        }

        token = new Token(ThreadLocalRandom.current().nextLong());
        tokens.put(user, token);
        tokensReversed.put(token, user);
        log.info("User " + user.getName() + " logged in");
        return token;
    }

    public static boolean validateToken(Token inputToken) {
        if (!tokensReversed.containsKey(inputToken)) {
            log.info("Unregistered user is trying to connect server with wrong token: " + inputToken.getId());
            return false;
        }
        log.info("Request from user " + tokensReversed.get(inputToken).getName() +  " with token: " + inputToken.getId());
        return true;
    }
}
