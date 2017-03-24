package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;


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
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;



@Path("/")
public class HackatonChatResource {
    private static final Logger log = LogManager.getLogger(HackatonChatResource.class);
    private static ConcurrentHashSet<String> loggined = new ConcurrentHashSet<>();
    private static ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        if (loggined.contains(name)) {
            log.info("User {} already loggined", name);
            return Response.status(Response.Status.BAD_REQUEST).entity("User already loggined").build();
        } else if (name.length() > 20) {
            log.info("Name {} so long", name);
            return Response.status(Response.Status.BAD_REQUEST).entity("Name so long").build();
        } else {
            log.info("User {} loggined ", name);
            loggined.add(name);
            messages.add("[" + name + "] joined to chat");
            return Response.ok().build();
        }
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        return Response.ok(String.join("\n", loggined)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/say")
    public Response say(@QueryParam("name") String name, @FormParam("msg") String msg) {
        if (!loggined.contains(name)) {
            log.info("User {} not loggined", name);
            return Response.status(Response.Status.BAD_REQUEST).entity("User not loggined").build();
        } else if (msg.length() > 100) {
            log.info("Message so long");
            return Response.status(Response.Status.BAD_REQUEST).entity("Message so long").build();
        }
        log.info("{}: {}", name, msg);
        StringBuilder builder = new StringBuilder();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        builder.append(format.format(date))
                .append("[")
                .append(name)
                .append("]:")
                .append(msg);
        messages.add(builder.toString());
        return Response.ok().build();

    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        return Response.ok(String.join("\n", messages)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@QueryParam("name") String name) {
        if (loggined.contains(name)) {
            log.info("User {} is logined out", name);
            StringBuilder builder = new StringBuilder();
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            builder.append(format.format(date))
                    .append(" user")
                    .append(name)
                    .append(" is loggined out");
            messages.add(builder.toString());
            loggined.remove(name);
            return Response.ok().build();
        } else {
            log.info("User {} is not loggined", name);
            return Response.status(Response.Status.BAD_REQUEST).entity("User is not loggined").build();
        }
    }
}
