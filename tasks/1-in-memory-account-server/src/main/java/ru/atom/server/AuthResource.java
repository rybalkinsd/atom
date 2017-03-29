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
    //private static final RegistratedUserStorage registered = new RegistratedUserStorage();
    private static final TokenStorage tokenStore = new TokenStorage();
    private static final LoginedUserStorage logined = new LoginedUserStorage();

    public static TokenStorage getTokenStore() {
        return tokenStore;
    }

    public static LoginedUserStorage getLogined() {
        return logined;
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
    public static Response
    register(@FormParam("user") String user, @FormParam("password") String password) {
        if (logined.isRegistered(user)) {
            log.info("User {} already registered", user);
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }
        if (password.length() < 3) {
            log.info("Password of {} is too short", user);
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short password").build();
        }
        User newUser = new User(user, password);
        logined.offer(newUser);
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
        if (logined.isRegistered(user)) {
            User currUser = logined.get(user);
            if (logined.isLogined(user)) {
                log.info("User {} already logined with token {}", user, currUser.getToken());
                return Response.ok("User " + currUser.getName() + " already logined with token "
                        + currUser.getToken()).build();
            } else {
                if (password.equals(currUser.getPassword())) {
                    Long newToken = TokenStorage.generateUniqueToken();
                    logined.get(user).setToken(newToken);
                    tokenStore.put(new Token(newToken), user);
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
            Token currT = new Token(token);
            if (AuthResource.getTokenStore().contains(currT)) {
                String name = AuthResource.getTokenStore().get(currT);
                logined.get(tokenStore.get(currT)).setToken(null);
                tokenStore.remove(currT);
                log.info("User {} is logouted", name);
                return Response.ok("User " + name + " is logouted").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
        }
    }

}