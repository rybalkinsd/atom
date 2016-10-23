package server.auth;

import matchmaker.MatchMaker;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;

import javax.ws.rs.core.Response;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/auth")
public class Authentication {

    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static MatchMaker matchMaker;

    static {
        matchMaker = new MatchMaker();
    }

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (UserStore.getInstance().putIfAbsent(user, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("login") String userName,
                                     @FormParam("password") String password) {

        if (userName == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            if (!authenticate(userName, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user

            Token token = TokenStore.getInstance().issueToken(userName,password);
            User user = TokenStore.getInstance().getUserbyToken(token);
            GameSession session = matchMaker.joinGame(user);
            log.info("User '{}' logged in", user);
            // Return the token on the response
            return Response.ok(Long.toString(token.getToken())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String user, String password) throws Exception {
        return password.equals(UserStore.getInstance().getPassword(user));
    }


    @Authorized
    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logoutUser(@HeaderParam("authorization") String strToken) {
        try {
            Token token = new Token(strToken.substring("Bearer".length()).trim());
            User user = TokenStore.getInstance().deleteToken(token);
            matchMaker.leaveGame(user);
            log.info("User '{}' logged out", user);
            return Response.ok("User " + user + " logged out.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
//
    @Authorized
    @POST
    @Path("changeName")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changeUserName(@HeaderParam("authorization") String strToken, @FormParam("name") String newName) {
        try {
            Token token = new Token(strToken.substring("Bearer".length()).trim());
            String oldName = TokenStore.getInstance().changeName(token, newName);
            String password = UserStore.getInstance().getPassword(oldName);
            if (UserStore.getInstance().remove(oldName)) {
                if (UserStore.getInstance().put(newName, password)) {
                    User user = TokenStore.getInstance().getUserbyToken(token);
                    log.info("User '{}'  changed name from {} to {}", user, oldName, newName);
                    return Response.ok("User " + user + " changed name from " + oldName + " to " + newName + ".").build();
                } else {
                    log.info("Such name is already busy");
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            } else {
                log.info("Such name does not exist");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
