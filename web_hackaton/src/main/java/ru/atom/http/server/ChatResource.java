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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Path("/chat")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final ConcurrentArrayQueue<String> logined =
            new ConcurrentArrayQueue<>();
    private static final ConcurrentArrayQueue<String> chat =
            new ConcurrentArrayQueue<>();

    private static Pattern urlPattern = Pattern.compile(
            "(?:https?|ftp):\\/\\/\\w[-_@.%#\\/?&=():,\\w]{2,}[a-zA-Z0-9\\/]");

    private String createDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss");
        return "<i style=\"color:#888888\">" + formatForDateNow.format(dateNow) + "</i>";
    }

    private String rgb(String name) {
        long hash = name.hashCode();
        long red = (hash
                & 0b1001001001001001001001001001001001001001001001001001001001001001L)
                % 255;
        long green = (hash
                & 0b0010010010010010010010010010010010010010010010010010010010010010L)
                % 255;
        long blue = (hash
                & 0b0100100100100100100100100100100100100100100100100100100100100100L)
                % 255;
        return String.format("%x%x%x", red, green, blue);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too long name, sorry :(").build();
        }
        if (name.toLowerCase().contains("gitler")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Gitler not allowed, sorry :(").build();
        }
        if (logined.contains(name)) {
            return Response.ok("Already logined").build();
        }
        log.info("[" + name + "] logined");
        logined.add(name);
        chat.add(createDate() + " <b style=\"color:#" + rgb(name) + "\">" +
                name + "</b> joined");
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        int loginedNum = logined.size();
        String response = "online " + loginedNum + " user(s)";
        if (loginedNum > 0)
            response += ":\n" + String.join(", ", logined);
        return Response.ok(response).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/say")
    public Response say(@QueryParam("name") String name, @FormParam("msg") String msg) {
        if (!logined.contains(name)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Not logined").build();
        }
        if (msg == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No message provided").build();
        }
        if (msg.length() > 140) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too long message").build();
        }
        log.info("[" + name + "]: " + msg);

        Matcher matcher = urlPattern.matcher(msg);
        if (matcher.find()) {
            msg = matcher.replaceAll("<a href=\"$0\">$0<\\a>");
        }

        chat.add(createDate() + " <b style=\"color:#" + rgb(name) + "\">" +
                name + "</b>: " + msg);
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        return Response.ok().entity(String.join("\n", chat)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        if (logined.contains(name)) {
            if (logined.remove(name)) {
                log.info("[" + name + "] logined out");
                chat.add(createDate() + " <b style=\"color:#" + rgb(name) + "\">" +
                        name + "</b> logined out");
                return Response.ok(name + " successfully logined out").build();
            } else return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong").build();
        } else {
            return Response.ok().build();
        }
    }
}
