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
import java.util.concurrent.ConcurrentLinkedQueue;

@Path("/chat")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final ConcurrentLinkedQueue<String> logined = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<String> chat = new ConcurrentLinkedQueue<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (name.toLowerCase().contains("hitler")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Hitler not allowed, sorry :(").build();
        }
        if (logined.contains(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        log.info("[" + name + "] logined");
        logined.add(name);
        chat.add("[" + name + "] joined");
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain;charset=UTF-8")
    @Path("/online")
    public Response online() {
        return Response.ok(String.join("\n", logined)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/say")
    public Response say(@QueryParam("name") String name, @FormParam("msg") String msg) {
        if (!logined.contains(name)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logined").build();
        }
        if (msg == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No message provided").build();
        }
        if (msg.length() > 140) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long message").build();
        }
        log.info("[" + name + "]: " + msg);
        chat.add("[" + name + "]: " + msg);
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain;charset=UTF-8")
    @Path("/chat")
    public Response chat() {
        return Response.ok(String.join("\n", chat)).build();
    }
}
