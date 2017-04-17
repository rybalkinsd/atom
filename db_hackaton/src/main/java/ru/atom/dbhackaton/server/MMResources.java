package ru.atom.dbhackaton.server;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by pavel on 17.04.17.
 */
@Path("/mm")
public class MMResources {

    @GET
    @Path("/join")
    @Produces("text/plain")
    public Response join(String body) {
        //TODO
        throw  new NotImplementedException();
    }

    @POST
    @Path("/finish")
    @Consumes("application/x-www-form-urlencoded")
    public Response finish(String jsonBody) {
        //TODO
        throw new NotImplementedException();
    }
}
