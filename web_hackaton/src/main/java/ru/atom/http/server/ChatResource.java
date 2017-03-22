package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import org.eclipse.jetty.util.ConcurrentHashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final ConcurrentHashSet<String> logined = new ConcurrentHashSet<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {

        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        final String string = formatter.format(now);
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (name.toLowerCase().contains("gitler")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Gitler not allowed, sorry :(").build();
        }
        if (logined.contains(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        log.info("[" + name + "] logined");
        logined.add(name);
        chat.add("</br>[" + "<span style='color:red'>" + name + "</span>" + " " + string + "] joined");
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
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        final String string = formatter.format(now);
        if (!logined.contains(name)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logined").build();
        }
        if (msg == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No message provided").build();
        }
        if (msg.length() > 140) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long message").build();
        }
        log.info("[" + name + " " + string + "]: " + msg);
        if (isMatch(msg)) {
            chat.add("[" + name + " " + string + "]: " + "<a href= " + msg + ">" + msg + "<a>");
        } else {
            chat.add("</br>[" + "<span style='color:red'>" + name + "</span>" + " " + string + "]: " + msg);
        }
        ChatHistory.saveMessage("[" + name + " " + string + "]: " + msg);
        return Response.ok().build();
    }

    @GET
    @Produces("text/html")
    @Path("/chat")
    public Response chat() {
        return Response.ok(String.join("\n", chat)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        final String string = formatter.format(now);
        if (logined.contains(name)) {
            logined.remove(name);
            chat.add("</br>[" + "<span style='color:red'>" + name + "</span>"
                    + " " + string + "] logout");
        } else {
            chat.add("</br>[" + "<span style='color:red'>" + name + "</span>"
                    + " " + string + "] who is " + name + "?");
        }
        return Response.ok().build();
    }

    private static boolean isMatch(String string) {
        String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return string.matches(regex);
    }
}
