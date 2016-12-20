package accountserver.api.auth;

import accountserver.database.leaderboard.LeaderboardDao;
import accountserver.database.tokens.Token;
import accountserver.database.tokens.TokenDao;
import accountserver.database.users.User;
import accountserver.database.users.UserDao;
import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationApi {
    private static final Logger log = LogManager.getLogger(AuthenticationApi.class);

    public static boolean validateToken(String rawToken) {
        Token token = ApplicationContext.instance().get(TokenDao.class).findByValue(rawToken);
        if (token == null) {
            return false;
        }
        log.info("Correct token from '{}'", ApplicationContext.instance().get(TokenDao.class).getTokenOwner(token));
        return true;
    }

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String username,
                             @FormParam("password") String password) {

        if (username == null || password == null) {
            log.error("Got null form parameters");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (username.equals("") || password.equals("") ||
                username.equals("null") || password.equals("null")) {
            log.info("Got invalid data (empty or 'null' user or password)");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        if (ApplicationContext.instance().get(UserDao.class).getUserByName(username) != null) {
            log.info("User '{}' already registered", username);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        User user = new User(username, password);
        ApplicationContext.instance()
                .get(UserDao.class)
                .addUser(user);

        //добавляем в таблицу Leaderboard
        ApplicationContext.instance()
                .get(LeaderboardDao.class)
                .addUser(user);

        log.info("New user '{}' registered", username);
        return Response.ok("User " + username + " registered.").build();
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("user") String username,
                                     @FormParam("password") String password) {

        if (username == null || password == null) {
            log.error("Got null form parameters");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (username.equals("") || password.equals("") || username.equals("null") || password.equals("null")) {
            log.info("Got invalid data (empty or 'null' user or password)");
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        try {
            // Authenticate the user using the credentials provided
            User user = ApplicationContext.instance().get(UserDao.class).getUserByName(username);
            if (user == null || !user.validatePassword(password)) {
                log.info("User '{}' is not exist", username);
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            // Issue a token for the user
            Token token = ApplicationContext.instance().get(TokenDao.class).generateToken(user);
            log.info("User '{}' logged in", user);

            // Return the token on the response
            return Response.ok(token.toString()).build();

        } catch (Exception e) {
            log.fatal(e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Authorized
    @Path("logout")
    @Produces("text/plain")
    public Response logout(@Context HttpHeaders headers) {
        Token token = AuthenticationFilter.getTokenFromHeaders(headers);
        if (token == null) {
            log.error("Got null token");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        ApplicationContext.instance().get(TokenDao.class).removeToken(token);
        return Response.ok("Logged out").build();
    }
}