package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.PrintWriter;

@Path("")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    //private static final ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();
    //private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();
    private static final ArrayList<String> logined = new ArrayList<>();
    private static final ArrayList<String> chat = new ArrayList<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (name.toLowerCase().contains("gitler")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Gitler not allowed, sorry :(").build();
        }
        if (logined.contains(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        log.info("[" + name + "] logined");
        logined.add(name);
        chat.add(dateFormat.format(new Date()) + " [" + name + "] joined");
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        log.info("[" + name + "]: " + msg);
        chat.add(dateFormat.format(new Date()).toString() + " [" + name + "]: " + msg);
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        return Response.ok(String.join("\n", chat)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        if (!logined.contains(name)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logined").build();
        } else {
            logined.remove(name);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            chat.add(dateFormat.format(new Date()).toString() + " [" + name + "]: " + " logout");
            return Response.ok().build();
        }
    }
}
