package server.api;

import model.Token;
import model.TokenStore;
import model.User;
import model.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api")
public class DataProvider {

    private static final Logger log = LogManager.getLogger(DataProvider.class);

    @Authorized
    @POST
    @Path("users")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response getUsers() {
        try {
            String users = UserStore.getInstance().writeJSONNames();
            log.info("UserStore got - {}", users);
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
