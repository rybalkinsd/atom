package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/chat")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);
    
    private static final ConcurrentArrayQueue<String> logined = new ConcurrentArrayQueue<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();
    
    private static String filePath = "C://Users/Serge/cygwin/atom/web_hackaton/src/main/resources/History.txt";

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
        write("[" + name + "] joined" + "\n");
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
        if (msg.equals("exit")) {
            log.info("[" + name + "] logged out");
            /*ConcurrentArrayQueue<String> logined1 = new ConcurrentArrayQueue<>();
            for(int i=0; i<logined.size(); ++i) {
            	logined1.add(logined.poll());           	
            }
            logined.clear();*/
            for(int i = 0; i < (logined.size()-1); ++i) {
            	logined.add(logined.element());
            	logined.poll();
            }
            logined.poll();
            LocalDateTime ldt = LocalDateTime.now();
            chat.add("[ (" + ldt.getHour()+ ":" + ldt.getMinute() + ") " + name + "] left");
            write("[" + name + "] left" + "\n");
        } else {
        	log.info("[" + name + "]: " + msg);
        	LocalDateTime ldt = LocalDateTime.now();
        	chat.add("[ (" + ldt.getHour()+ ":" + ldt.getMinute() + ") " + name + "]: " + msg);
        	write("[" + name + "]: " + msg + "\n");
        	
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
    public Response logout(@QueryParam("name") String name) {
    	
    	if(!logined.contains(name)){
    		return Response.status(Response.Status.BAD_REQUEST).entity("No such user :(").build();
    	}
        log.info("[" + name + "] logged out");
        logined.remove(name);
        chat.add("[" + name + "] left");
        write("[" + name + "] left" + "\n");
        return Response.ok().build();
    }
    
    public static void write(String text) {
        try {
            Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
