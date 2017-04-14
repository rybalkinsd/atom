package ru.atom.dbhackaton.auth;


import com.sun.jna.platform.win32.WinDef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.model.Token;
import ru.atom.dbhackaton.model.TokenStorage;
import ru.atom.dbhackaton.model.User;
import ru.atom.dbhackaton.model.UserStorage;

import java.sql.Timestamp;
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

import static ru.atom.dbhackaton.model.TokenStorage.*;
import static ru.atom.dbhackaton.model.UserStorage.getByName;
import static ru.atom.dbhackaton.model.UserStorage.insert;

/**
 * Created by vladfedorenko on 26.03.17.
 */

@Path("/")
public class AuthOps {
    private static final Logger log = LogManager.getLogger(AuthOps.class);

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null || user.equals("") || password.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (getByName(user) != null) {
            log.warn(user + " trying to get protected name");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            RegistredEntity newUser = new RegistredEntity(user, password, timestamp);
            insert(newUser);
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
        String logoutName = getByToken(getTokenFromContext(requestContext)).getLogin();
        logoutToken(logoutName);
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
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String user, String password) throws Exception {
        RegistredEntity userProfile = getByName(user);
        return userProfile != null && userProfile.getPassword().equals(password);
    }

    private long issueToken(String user) {
        LoginEntity login = getLoginByName(user);
        if (login != null) {
            return Long.parseLong(login.getToken());
        }
        RegistredEntity userObject = getByName(user);
        Long tokenLong = ThreadLocalRandom.current().nextLong();
        while (getByToken(tokenLong)!=null) {
            tokenLong = ThreadLocalRandom.current().nextLong();
        }
        LoginEntity newLogin = new LoginEntity();
        newLogin.setToken(Long.toString(tokenLong));
        newLogin.setLogin(user);
        saveLogin(newLogin);
        log.info("Set token " + tokenLong + " to " + user);
        return tokenLong;
    }

    static void validateToken(Long token) throws Exception {
        LoginEntity login = getByToken(token);
        if (login == null) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", login.getLogin());
    }

    private static Long getTokenFromContext(ContainerRequestContext requestContext) {
        return Long.parseLong(requestContext.getHeaderString("Authorization")
                .substring("Bearer: ".length()).trim());
    }

//
//    public static String getUsers() throws JsonProcessingException {
//        HashMap tmp = new HashMap<String, List<String>>();
//        tmp.put("users", tokenStorage.getUsers());
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(tmp);
//    }
}
