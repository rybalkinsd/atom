package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Iterator;

@Path("")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);


    private static final ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
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
    @Produces("text/plain")
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
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        return Response.ok(String.join("\n", chat)).build();
    }

    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        log.info("[" + name + "] logout");
        Iterator<String> it = logined.iterator();
        while (it.hasNext())
        {
            String pe = it.next();
            if(!pe.equals(name))
            {
                logined.peek();
            }
            else logined.remove();
        }
        return Response.ok(String.join("\n", logined)).build();
    }
}
