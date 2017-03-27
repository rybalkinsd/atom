package ru.atom.resources;

import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by mkai on 3/26/17.
 */

@Path("/data")
public class DataResource {

    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Path("/users")
    @Produces("application/json")
    public Response users() {
        Gson gson = new Gson();
        return Response.ok().build();
    }
}
