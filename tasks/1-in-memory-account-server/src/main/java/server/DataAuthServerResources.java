package server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import resources.User;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Robin on 29.03.2017.
 */

@Path("data")
public class DataAuthServerResources {
    private static final Logger log = LogManager.getLogger(DataAuthServerResources.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response display() {
        Gson gson = new Gson();
        ConcurrentHashMap<String, Collection<User>> response = new ConcurrentHashMap<>();
        response.put("users",  AuthServerResources.loggedUsers.returnAllUsers());
        log.info("Request to display users");
        return Response.ok(gson.toJson(response)).build();
    }


}
