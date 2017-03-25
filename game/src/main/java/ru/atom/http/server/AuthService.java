package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.resource.Token;
import ru.atom.resource.TokenStorage;
import ru.atom.resource.User;
import ru.atom.resource.UsersStorage;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zarina on 23.03.17.
 */
@Path("/auth/")
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);
    private static final UsersStorage users = new UsersStorage();
    private static final TokenStorage tokens = new TokenStorage();

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("user") String name, @FormParam("password") String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        log.info("Register user " + name);
        if (users.get(name) == null) {
            log.info("New user " + name);
            User user = new User(name, password);
            users.put(name, user);
            return  Response.ok("ok").build();
        }
        log.info("User exist " + name);
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("user") String name, @FormParam("password") String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        log.info("Login user " + name);
        User user = users.get(name);
        if (user == null) {
            log.info("User " + name + " not found");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!user.validPassword(password)) {
            log.info("Invalid password for user " + name);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Long tokenNum = tokens.getToken(user);
        if (tokenNum == null) {
            Token token = new Token(user);
            tokenNum = token.getToken();
            tokens.put(tokenNum, token);
        }
        return  Response.ok(tokenNum.toString()).build();
    }

    @Authorized
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String token_param) {
        Long token = Long.parseLong(token_param.substring("Bearer".length()).trim());
        log.info("Logout with token " + token);
        tokens.remove(token);
        return  Response.ok("ok").build();
    }

    public static String getAllUsers() {
        log.info("All users " + users);
        return users.toString();
    }

    public static boolean validToken(String token) {
        log.info("Valid token " + token);
        return tokens.validToken(token);
    }
}
