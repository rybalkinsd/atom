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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Path("/")
public class ChatResource {

    private static final ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();
    private static final Logger log = LogManager.getLogger(ChatResource.class);
    //FileWriter fileW;
    //FileReader fileR;

    public ChatResource() throws IOException {
    // fileW = new FileWriter("C:/Users/Ксения/atom/web_hackaton/src/main/resources/History.txt", true);
        // while (fileR.)
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
        if (msg.contains("http:") || msg.contains("https:") || msg.contains("www.") || msg.matches("http || www")) {
            msg = "<a href=\"" + msg + "\">" + msg + "</a>";
        }

        String dataColored = "<span style=\"background-color:red\">" +  "[" + new Date() + "] " + "</span>";
        String nameColored = "<span style=\"background-color:green\">" +  "[" + name + "] " + "</span>";
        String msgColored = "<span style=\"background-color:blue\">" + msg + "</span>";


        msg = nameColored + " " + dataColored + " " + msgColored;
        if (chat.contains(msg)) {
            spam();
        }
        log.info(msg);
        chat.add(msg);

        //        try {
        //            fileW.write(msg);
        //            fileW.append('\n');
        //            fileW.flush();
        //            log.info("Message { } was written", msg);
        //        } catch (Exception ex) {
        //            log.warn("Message { } wasn't written", msg);
        //        }
        return Response.ok().build();
    }

    @GET
    @Path("/say")
    @Produces(value = MediaType.TEXT_HTML)
    public String spam() {
        return "<script>alert('Don't spam!!!!');</script>";
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
        if (logined.remove(name)) {                             //удаляет только последнего пользователя
            log.info("[" + name + "] logouted");
            chat.add("[" + name + "] logouted");
        } else log.info("[" + name + "] wasn't logined");
        return Response.ok().build();
    }
}
