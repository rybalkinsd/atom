package io.github.rentgen94.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static io.github.rentgen94.server.ServerResources.authUsers;

/**
 * Created by Western-Co on 27.03.2017.
 */
@Path("data")
public class DataResources {
    public DataResources() {}

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response showUsers() {
        return Response.ok(authUsers.getAllUserInJson()).build();
    }
}
