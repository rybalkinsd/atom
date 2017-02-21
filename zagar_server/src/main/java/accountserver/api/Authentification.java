package accountserver.api;

import accountserver.authDAO.LB;
import accountserver.authDAO.TokenDAO;
import accountserver.authDAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import accountserver.authInfo.Token;
import accountserver.authInfo.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


/**
 * Created by User on 20.10.2016.
 */
@Path("/auth")
public class Authentification {

    private static final Logger log = LogManager.getLogger(Authentification.class);
    public static UserDAO userDAO;
    public static TokenDAO tokenDAO;
    public static LB LB;

    static {
        userDAO = new UserDAO();
        tokenDAO = new TokenDAO();
        LB = new LB();
    }


    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user")String login, @FormParam("password")String password ){
        try {
            if (login == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (userDAO.getUserByLoginData(login,password) != null) {
                log.info("User with login={} and password={} already exists", login, password);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            User newUser = new User(login, password);
            userDAO.insert(newUser);
            LB.insert(newUser.getId());
            log.info("New user '{}' registered", newUser);
            return Response.ok("User " + newUser + " registered").build();
        } catch (Exception e){
            log.error("Error register user.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("user")String login, @FormParam("password")String password ){
        try {
            if (login == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User currentUser = userDAO.getUserByLoginData(login, password);
            if (currentUser == null) {
                log.info("Wrong password for user login={} password={}", login, password);
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            Token currentToken = tokenDAO.getTokenByUserId(currentUser.getId());
            if(currentToken != null){
                log.info("User '{}' already logged in.", currentUser);
                return Response.ok(currentToken.getNumber()).build();
            }

            currentToken = new Token(currentUser.getId());
            tokenDAO.insert(currentToken);
            log.info("User '{}' logged in.", currentUser);
            return Response.ok(currentToken.getNumber()).build();

        } catch (Exception e){
            log.info("Error login user.");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    public static boolean validateToken(String stringToken) throws Exception{
        Token requestedToken = tokenDAO.getTokenByStringToken(stringToken);
        if(requestedToken != null){
            log.info("Correct token from '{}'", userDAO.getUserById(tokenDAO.getUserIdByStringToken(stringToken)));
            return true;
        } else{
            throw new Exception("Token validation exception");
        }
    }

    @Autorized
    @POST
    @Path("/logout")
    @Produces("text/plain")
    public Response logout(@Context HttpHeaders headers){
        try{
            String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
            String token = authorizationHeader.substring("Bearer".length()).trim();
            tokenDAO.deleteByStringToken(token);
            log.info("User with token='{}' logged out", token);
            return Response.ok("User logged out").build();
        } catch(Exception e) {
            log.info("Error logout user.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }




}
