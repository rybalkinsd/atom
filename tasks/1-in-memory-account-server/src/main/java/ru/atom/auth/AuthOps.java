package ru.atom.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.Token;
import ru.atom.model.TokenStorage;
import ru.atom.model.User;
import ru.atom.model.UserStorage;

import java.util.UUID;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vladfedorenko on 26.03.17.
 */

@Path("/auth")
public class AuthOps {
    private static TokenStorage tokenStorage = new TokenStorage();
    private static UserStorage userStorage = new UserStorage();
    private static final Logger log = LogManager.getLogger(AuthOps.class);

    static {
        User admin = new User("admin", "admin");
        userStorage.addUser("admin", admin);
        log.info("Admin registered");
        Token adminToken = new Token(admin, 0);
        tokenStorage.addToken(adminToken);
        log.info("Admin authorized");

    }

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null || user.equals("") || password.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (userStorage.getUser(user) != null) {
            log.warn(user + " trying to get protected name");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } else {
            User newUser = new User(user, password);
            userStorage.addUser(user, newUser);
            log.info("New user '{}' registered", user);
            return Response.ok("User " + user + " registered.").build();
        }

    }


    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Authorized
    public Response logout(ContainerRequestContext requestContext) {
        Token token = tokenStorage.getToken(getTokenFromContext(requestContext));
        String logoutName = token.getUser().getName();
        tokenStorage.removeToken(token);
        log.info("Logout: " + logoutName);
        return Response.ok("Logout: " + logoutName).build();
    }


    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {
        if (user == null || password == null) {
            log.info("Empty login or empty pass");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            if (!authenticate(user, password)) {
                log.warn(user + " trying to login with wrong password");
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Long token = issueToken(user);
            log.info("User '{}' logged in", user);
            return Response.ok("Bearer: " + token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String user, String password) throws Exception {
        User userProfile = userStorage.getUser(user);
        return userProfile != null && userProfile.checkPass(password);
    }

    private long issueToken(String user) {
        if (tokenStorage.getTokenForUser(user) != null) {
            return tokenStorage.getTokenForUser(user).getToken();
        }
        User userObject = userStorage.getUser(user);
        Long tokenLong = ThreadLocalRandom.current().nextLong();
        while (tokenStorage.validateToken(tokenLong)) {
            tokenLong = ThreadLocalRandom.current().nextLong();
        }
        Token token = new Token(userObject, tokenLong);
        tokenStorage.addToken(token);
        log.info("Set token " + token + " to " + user);
        return tokenLong;
    }

    static void validateToken(Long token) throws Exception {
        if (!tokenStorage.validateToken(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokenStorage.getToken(token).getUser().getName());
    }

    private static Long getTokenFromContext(ContainerRequestContext requestContext) {
        return Long.parseLong(requestContext.getHeaderString("Authorization")
                .substring("Bearer: ".length()).trim());
    }

    public static String getUsers() throws JsonProcessingException {
        HashMap tmp = new HashMap<String, List<String>>();
        tmp.put("users", tokenStorage.getUsers());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(tmp);
    }
}
