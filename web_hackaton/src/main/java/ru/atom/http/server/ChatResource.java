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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Path("/chat")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);
    private static final ConcurrentLinkedQueue<String> logined = new ConcurrentLinkedQueue<>();
    private static final ConcurrentArrayQueue<String> chat = loadHistory();

    public ChatResource() {
        loadHistory();
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
        chat.add("[<b><font color=#0000FF>" + name + "</font></b>] joined");
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private String createDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss");
        return "<i>[" + formatForDateNow.format(dateNow) + "]</i>";
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
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd 'и время' hh:mm:ss a zzz");

        if (msg.contains("http://") || msg.contains("https://")) {
            msg = "<a href=\"" + msg + "\">" + msg + "</a>";
        }

        chat.add(createDate() + " <b><font style=\"color: " + nameColor(name) + "\">" + name + "</font></b>: " + msg);
        saveHistory();
        return Response.ok().build();
    }

    private static String nameColor(String name) {
        int rgb1 = name.hashCode() >> 24;
        int rgb2 = (name.hashCode() >> 16) & 255;
        int rgb3 = (name.hashCode() >> 8) & 255;
        return "rgb(" + rgb1 + "," + rgb2 + "," + rgb3 + ")";
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
            log.info("[" + name + "] logged out");
            chat.add("<b><font color=#0000FF>" + name + "</font></b> logged out");
        }
        return Response.ok().build();
    }

    private static void saveHistory() {
        File file = new File("history.txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.join("\n", chat));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ConcurrentArrayQueue<String> loadHistory() {
        ConcurrentArrayQueue<String> strings = new ConcurrentArrayQueue<>();
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("history.txt"), StandardCharsets.UTF_8);
            for (String line: lines) {
                strings.add(line);
            }
        } catch (IOException e) {
            ;
        }
        return strings;
    }
}
