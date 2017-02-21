package accountserver.auth;

import model.Token;
import model.TokensCollection;
import model.TokensCollectionImpl;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by sl on 19.10.2016.
 */

@Path("/auth")
public class Authentication {
    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static final TokensCollection data = new TokensCollectionImpl();

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String user,
                            @FormParam("password") String password) {

        System.out.println(user+"  "+ password);
        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "pass or name undefined").build();
        }

        try {
            data.addUser(new User(user, password));
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).header("reason", "such name is taken").build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("user") String user,
                                     @FormParam("password") String password) {

        if (user == null || password == null)
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "pass or name undefined").build();

        User client=new User(user, password);
        if (!data.authenticate(client))
            return Response.status(Response.Status.UNAUTHORIZED).header("reason", "such name is taken").build();

        Token token=data.issueToken(client);
        log.info("User '{}' logged in", user);
        // Return the token on the response
        return Response.ok(token.toString()).build();
    }

    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response outUser() {
        return Response.ok("You logged out").build();
    }

}
