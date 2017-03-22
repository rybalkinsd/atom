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
import java.io.*;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);
    private static final ConcurrentLinkedQueue<String> logined = new ConcurrentLinkedQueue<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();
    private static String fileName = new String("history");
    private static final ExtraInit ex = new ExtraInit().makeinit();


    static void readFromFile(){
//        System.out.println("read");
        File file = new File(fileName);
        if (file.exists()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
                try {
//                    System.out.println("read in try");
                    String s;
                    while ((s = in.readLine()) != null) {
//                        System.out.println("read read");
                        chat.add(s);
                    }
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                System.err.println("error");
            }
        }
    }

    void saveToFile(){
//        System.out.println("save");
        File file = new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            for (String str : chat){
//                System.out.println("saving");
                out.println(str);
            }
            out.close();
        }catch (IOException e){
            System.err.println("error");
        }
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
        saveToFile();
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
        long time = System.currentTimeMillis() / 1000 / 60;
        LocalDateTime date = LocalDateTime.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        long min = time % 60;
        long hours = time / 60;
        hours = hours % 24 + 3;
        if (hours > 24) {
            hours = hours - 24;
        }
        if (msg.contains("http:/")) {
            String msg1 = new String(msg.substring(0, msg.indexOf("http:/")));
            log.info("1" + msg1);
            String msg2 = new String(msg.substring(msg.indexOf("http:/"), msg.indexOf(".com") + 4));
            log.info("2" + msg2);
            String msg3 = new String(msg.substring(msg.indexOf(".com") + 4));
            log.info("3" + msg3);
            chat.add("[" + name + "]: " + msg1 + "<a href=msg2>" + msg2 + "</a>" + msg3+" <font color=0D823B>" + day + "." + month + "." + year + " " + hours + ":" + min + "</font>");
        } else{
            log.info("[" + name + "]: " + msg + " " + day + "." + month + "." + year + " " + hours + ":" + min);
            chat.add("[" + name + "]: " + msg + " <font color=0D823B>" + day + "." + month + "." + year + " " + hours + ":" + min + "</font>");
        }
        saveToFile();
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
            return Response.status(Response.Status.BAD_REQUEST).entity("There isn't such user :(").build();
        }
        log.info("[" + name + "]: has been logged out");
        logined.remove(name);
        System.out.println(logined.contains(name));
        chat.add("[" + name + "]: has been logged out");
        saveToFile();
        return Response.ok().build();
    }
}

