package server.auth;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.PrintFormat;
import org.jetbrains.annotations.NotNull;
import server.entities.Token;
import server.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ConcurrentHashMap<User, Token> tokens;
    private static ConcurrentHashMap<Token, User> tokensReversed;
    private static CopyOnWriteArrayList<User> serverUsers;

    static {
        tokens = new ConcurrentHashMap<>();
        tokensReversed = new ConcurrentHashMap<>();
        serverUsers = new CopyOnWriteArrayList<>();
    }

    /*curl -i \
          -X POST \
          -H "Content-Type: application/x-www-form-urlencoded" \
          -H "Host: localhost:8080" \
          -d "login=qq&password=qq" \
     "http://localhost:8080/auth/register"*/

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String name,
                             @FormParam("password") String password) {

        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = getUserByName(name);

        if (user != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        user = new User(name, password);
        serverUsers.add(user);
        log.info("New user '{}' registered", name);
        return Response.ok("User " + name + " registered.").build();
    }

    /*curl -X POST \
          -H "Content-Type: application/x-www-form-urlencoded" \
          -H "Host: localhost:8080" \
          -d "login=qq&password=qq" \
     "http://localhost:8080/auth/login"*/
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
            Token token = issueToken(user);
            log.info("User '{}' successfully logged in", user);
            return Response.ok(Long.toString(token.getToken())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /*curl -X POST \
            -H "Authorization: Bearer 3008650623990024943" \
            -H "Host: localhost:8080" \
            "http://localhost:8080/auth/logout"*/

    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response logoutPlayer(@HeaderParam("Authorization") String rawToken) {
        try {
            Long longToken = Long.parseLong(rawToken.substring("Bearer".length()).trim());
            Token token = new Token(longToken);
            if (!tokensReversed.containsKey(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                User user = tokensReversed.get(token);
                tokens.remove(user);
                tokensReversed.remove(token);
                if (log.isInfoEnabled()) {
                    log.info("User '{}' logout successfully", user.getName());
                }
                return Response.ok(user.getName() + " successfully logout!").build();
            }
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn(e);
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String name, String password) throws Exception {
        return serverUsers.stream()
                .filter(usr -> usr.getName().equals(name) && usr.checkPassword(password))
                .count() == 1;
    }

    private User getUserByName(@FormParam("login") String name) {
        return serverUsers.stream()
                .filter(usr -> usr.getName().equals(name))
                .findAny().orElse(null);
    }

    private Token issueToken(String name) {
        User user = getUserByName(name);
        Token token = tokens.get(user);
        if (token != null) {
            return token;
        }
        Long newToken = ThreadLocalRandom.current().nextLong();
        Token currentToken = new Token(newToken);
        log.info("Generate new token {} for user {}", currentToken, user);
        tokens.put(user, currentToken);
        tokensReversed.put(currentToken, user);
        return currentToken;
    }

    static void validateToken(String rawToken) throws Exception {
        Long longToken = Long.parseLong(rawToken);
        Token token = new Token(longToken);
        if (!tokensReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokensReversed.get(token));
    }

    @NotNull
    public static CopyOnWriteArrayList<User> getServerUsers() {
        return serverUsers;
    }

    @NotNull
    public static ConcurrentHashMap<Token, User> getTokensReversed() {
        return tokensReversed;
    }

    @NotNull
    public static ConcurrentHashMap<User, Token> getTokens() {
        return tokens;
    }
}
