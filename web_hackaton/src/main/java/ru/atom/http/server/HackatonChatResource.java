package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/chat")
public class HackatonChatResource {
    private static final Logger log = LogManager.getLogger(HackatonChatResource.class);

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/say")
    public Response say(@QueryParam("name") String name, @FormParam("msg") String msg) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
