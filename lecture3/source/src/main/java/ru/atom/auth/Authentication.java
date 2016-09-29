package ru.atom.auth;

/**
 * Created by s.rybalkin on 28.09.2016.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/")
public class Authentication {


    // curl -H 'Authorization: Bearer 2133e36c-8f31-455f-840e-1e034d4975fd' http://localhost:8080/dummy
    @Authorized
    @GET
    @Path("dummy")
    public Response dummy() {
        return Response.ok().build();
    }

    @POST
    @Path("login")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("login") String login,
                                     @FormParam("password") String password) {

        try {
            // Authenticate the user using the credentials provided
            authenticate(login, password);

            // Issue a token for the user
            String token = issueToken(login);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    }

    private String issueToken(String username) {
        return UUID.randomUUID().toString();
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    }
}
