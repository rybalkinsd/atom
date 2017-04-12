package ru.atom.dbhackaton.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;






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
