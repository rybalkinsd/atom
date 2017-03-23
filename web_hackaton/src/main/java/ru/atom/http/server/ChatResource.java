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
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final ConcurrentLinkedQueue<String> logined = new ConcurrentLinkedQueue<>();
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
        chat.add("[<span style=\"color:red\">" + name +
                 "</span> <span style=\"color:green\">" + new Date(System.currentTimeMillis()) + "</span>]: joined");
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        return Response.ok(String.join("\n ", logined)).build();
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
        if (msg.length() > 130) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long message (longer than 140" +
                    " symbols)").build();
        }
        log.info("[" + name + "]: " + msg);
        Pattern pattern = Pattern.compile(
                "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])");
        Matcher match = pattern.matcher(msg);
        while (match.find()) {
            msg = match.replaceFirst("<a href=\"" + msg.substring(match.start(), match.end()) + "\">" +
                    msg.substring(match.start(), match.end()) + "</a>");
        }
        chat.add("[<span style=\"color:red\">" + name
                + "</span> <span style=\"color:green\">" + new Date(System.currentTimeMillis()) + "</span>]: " + msg);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        return Response.ok(String.join("\n ", chat)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        if (!logined.contains(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not logined").build();
        }
        logined.remove(name);
        log.info("[" + name + "]: logged out");
        chat.add("[<span style=\"color:red\">" + name +
                "</span> <span style=\"color:green\">" + new Date(System.currentTimeMillis()) + "</span>]: logged out");
        return Response.status(Response.Status.OK).build();
    }
}
