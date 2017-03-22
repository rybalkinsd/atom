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
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) throws FileNotFoundException {
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (logined.contains(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        log.info(name + " logined");
        logined.add(name);
        chat.add("[" + "<font color=\"green\">" + name + "</font>" + "] joined");
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
    public Response say(@QueryParam("name") String name, @FormParam("msg") String msg) throws FileNotFoundException {

        if (!logined.contains(name)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logined").build();
        }
        if (msg == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No message provided").build();
        }
        if (msg.length() > 150) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long message").build();
        }

        log.info(name + " say: " + msg);

        if (msg.contains("http:")) {
            chat.add("<font color=\"green\">" + name + "</font>"
                    + " " + "<font color=\"red\">" + LocalDateTime.now()
                    + "</font>" + "]: " + "<a href=\"" + msg + "\">" + msg + "</a>");
        } else {
            chat.add("[" + "<font color=\"green\">" + name + "</font>"
                    + " " + "<font color=\"red\">" + LocalDateTime.now() + "</font>" + "]: " + msg);
        }

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
    public Response logout(@QueryParam("name") String name) throws FileNotFoundException {
        if (!logined.contains(name)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Did not logined").build();
        } else {
            logined.remove(name);
            log.info("[" + name + "]: left ");
            chat.add("[" + name + "]" + " left");
            return Response.ok().build();
        }
    }
}