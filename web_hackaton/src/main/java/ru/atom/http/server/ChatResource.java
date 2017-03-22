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
import java.util.Calendar;

@Path("/")
public class ChatResource {
    private static final ChatDAO chatDAO = new ChatDAO("/Users/dmbragin/test.txt");
    private static final Logger log = LogManager.getLogger(ChatResource.class);
    private static final ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();
    private static final ConcurrentArrayQueue<String> chat = chatDAO.getAll();
    private static final Calendar cal = Calendar.getInstance();
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");


    private static String getColoredString(String user, String separator, String msg) {
        String result = "<font color=\"green\">" + time.format(cal.getTime()) + "</font>" +
                "[" +
                "<font color=\"#DF013A\">" +
                user +
                "</font>" +
                "]" + separator +
                " " + msg;
        return result;
    }

    private void addInfoToChat(String coloredString) {
        chat.add(coloredString);
        chatDAO.write(coloredString);
    }

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

        logined.add(name);
        log.info(time.format(cal.getTime()) + "[" + name + "] logined");
        String coloredString = getColoredString(name, " ", "logined");
        addInfoToChat(coloredString);
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

        log.info(time.format(cal.getTime()) + "[" + name + "]:" + msg);
        String coloredString = getColoredString(name, ":", msg);
        addInfoToChat(coloredString);
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
        if (logined.contains(name)) {
            logined.remove(name);

            String coloredString = getColoredString(name, " ", "logout");
            addInfoToChat(coloredString);
            log.info("[" + name + "] logout");
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("User is not login on chat").build();
        }
    }
}
