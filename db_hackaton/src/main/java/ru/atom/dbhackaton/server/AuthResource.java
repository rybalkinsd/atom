package ru.atom.dbhackaton.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.TokenStorage;
import ru.atom.dbhackaton.resource.User;
import ru.atom.dbhackaton.resource.UsersStorage;
import ru.atom.dbhackaton.service.AuthException;
import ru.atom.dbhackaton.service.AuthService;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by BBPax on 23.03.17.
 */
// TODO: 14.04.17   есть только запись юзеров в БД при регистрации и подгрузка их из БД при запуске сервера
@Path("/")
public class AuthResource {
    private static final Logger log = LogManager.getLogger(AuthResource.class);
    private static final AuthService authService = new AuthService();
    protected static final UsersStorage users = new UsersStorage().setUp();
    protected static final TokenStorage tokens = new TokenStorage().setUp();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("user") String name, @FormParam("password") String password) {
        if (name == null || name.isEmpty()) {
            log.info("Login is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Login, pls").build();
        }
        if (password == null || password.isEmpty()) {
            log.info("Password is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Password, pls").build();
        }
        if (password.length() < 5) {
            log.info("Password is too short");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password is too short. Minimal length is 5").build();
        }

        log.info("Registration of user: " + name);
        User user = new User().setLogin(name).setPassword(password);

        try {
            authService.register(user);
            log.info("New user \"" + name + "\" created");
            users.put(name, user);
        } catch (AuthException e) {
            log.info("User \"" + name + "\" exists");
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registrated").build();
        }
        return Response.ok("ok").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("user") String name, @FormParam("password") String password) {
        if (name == null || name.isEmpty()) {
            log.info("Login is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Login, pls").build();
        }
        if (password == null || password.isEmpty()) {
            log.info("Password is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Password, pls").build();
        }
        log.info("Login user " + name);
        User user = users.get(name);
        if (user == null) {
            log.info("User " + name + " not found");
            return Response.status(Response.Status.BAD_REQUEST).entity("User " + name + " not found").build();
        }
        if (!user.validPassword(password)) {
            log.info("Invalid password for user " + name);
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid password for user " + name).build();
        }
        Long tokenNum = tokens.getToken(user);
        if (tokenNum == null) {
            Token token = new Token().setUser(user).setToken(0L);
            tokenNum = token.getToken();
            authService.login(token);
            log.info("User \"" + name + "\" login with generated token: " + tokenNum);
            tokens.put(tokenNum, token);
        }
        return  Response.ok(tokenNum.toString()).build();
    }

    @Authorized
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) {
        Long token = Long.parseLong(tokenParam.substring("Bearer".length()).trim());
        log.info("Logout with token " + token);
        try {
            authService.logout(tokens.get(token));
            tokens.remove(token);
        } catch (NullPointerException e) {
            log.info("User with token " + token + " is not logined");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("User with token " + token + " is not logined").build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces("application/json")
    @Path("data/users")
    // TODO: 14.04.17   это вроде как пока не нужно
    public Response online() {
        Gson gson = new Gson();
        HashMap<String, List<User>> jsonResp = new HashMap<>();
        jsonResp.put("users",  getOnlineUsers());
        return Response.ok(gson.toJson(jsonResp)).build();
    }

    public static LinkedList<User> getOnlineUsers() {
        LinkedList<User> users = new LinkedList<>();
        for (Token token : tokens.getAll()) {
            users.add(token.getUser());
        }
        log.info("Online users " + users);
        return users;
    }

    public static boolean validToken(String token) {
        log.info("Token validаtion: " + token);
        return tokens.validToken(token);
    }
}
