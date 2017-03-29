package ru.atom.server;

/**
 * Created by Ксения on 24.03.2017.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


@Path("auth")
public class AuthResource {

    private static final Logger log = LogManager.getLogger(AuthResource.class);
    private static final RegistratedUserStorage registered = new RegistratedUserStorage();
    private static final TokenStorage tokenStore = new TokenStorage();
    private static final LoginedUserStorage logined = new LoginedUserStorage();

    public static TokenStorage getTokenStore() {
        return tokenStore;
    }

    public static LoginedUserStorage getLogined() {
        return logined;
    }

    public static RegistratedUserStorage getRegistered() {
        return registered;
    }

    @GET
    @Produces("text/plain")
    public Response sayHello() {
        return Response.ok("Hello")
                .build();
    }

    //Protocol: HTTP
    //Path: auth/register
    //Method: POST
    //Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
    //Headers:
    //Content-Type: application/x-www-form-urlencoded
    //Body:
    //user={}&password={}
    //Response:
    //Code: 200
    //Content-Type: text/plain
    // Body: сообщение об успехе


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    @Produces("text/plain")
    public static Response register(@FormParam("user") String user, @FormParam("password") String password) {
        if (registered.containsUser(user)) {
            log.info("User {} already registered", user);
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }
        if (password.length() < 3) {
            log.info("Password of {} is too short", user);
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short password").build();
        }
        User newUser = new User(user, password);
        registered.offer(newUser);
        log.info("New user {} is registrated with id {}", newUser.getName(), newUser.getId());
        return Response.ok("New user " + newUser.getName() + " is registrated")
                .build();
    }

    //    Protocol: HTTP
    //    Path: auth/login
    //    Method: POST
    //    Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
    //    Headers:
    //    Content-Type: application/x-www-form-urlencoded
    //    Body:
    //    user={}&password={}
    //    Response:
    //    Code: 200
    //    Сontent-Type: text/plain
    //    Body: token

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    @Produces("text/plain")
    public static Response login(@FormParam("user") String user, @FormParam("password") String password) {
        if (registered.containsUser(user)) {
            User currUser = registered.getUserByName(user);
            if (logined.isLogined(currUser.getId())) {
                log.info("User {} already logined with token {}", currUser.getName(), currUser.getToken());
                return Response.ok("User " + currUser.getName() + " already logined with token "
                        + currUser.getToken()).build();
            } else {
                if (password.equals(currUser.getPassword())) {
                    Long newToken = TokenStorage.generateUniqueToken();
                    registered.getUserById(currUser.getId()).setToken(newToken);

                    if (logined.containsUser(currUser.getId())) {
                        logined.getUserById(currUser.getId()).setToken(newToken);
                        tokenStore.getToken(currUser.getId()).setToken(newToken);
                    } else {
                        logined.offer(currUser.setToken(newToken));
                        tokenStore.offer(currUser.getId(), newToken);
                    }
                    log.info("User {} logined with new token {}", currUser.getName(), currUser.getToken());
                    return Response.ok("User " + currUser.getName() + " logined with new token "
                            + currUser.getToken()).build();
                } else {
                    log.info("Wrong password for {}", currUser.getName());
                    return Response.status(Response.Status.BAD_REQUEST).entity("Wrong password for"
                            + currUser.getName()).build();
                }
            }
        }
        log.info("User {} isn't registered", user);
        return Response.status(Response.Status.BAD_REQUEST).entity("User " + user + " isn't registered").build();
    }


    //  Protocol: HTTP
    //          Path: auth/logout
    //          Method: POST
    //          Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
    //          Headers:
    //          Authorization: Bearer {token}
    //          Response:
    //          Code: 200
    //          Сontent-Type: text/plain
    //          Body: сообщение об успехе
    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    @Produces("text/plain")
    public static Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenStr) {
        try {
            Long token = Long.parseLong(tokenStr.trim());
            if (AuthResource.getTokenStore().containsToken(token)) {
                Integer id = AuthResource.getTokenStore().getTokenId(token);
                tokenStore.getToken(id).setToken(null);
                logined.getUserById(id).setToken(null);
                registered.getUserById(id).setToken(null);
                log.info("User {} is logouted", registered.getUserById(id).getName());
                return Response.ok("User " + registered.getUserById(id).getName() + " is logouted").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
        }
    }

}