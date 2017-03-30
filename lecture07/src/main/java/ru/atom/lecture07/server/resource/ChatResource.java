package ru.atom.lecture07.server.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;
import ru.atom.lecture07.server.service.ChatException;
import ru.atom.lecture07.server.service.ChatService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);
    private static final ChatService chatService = new ChatService();


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name) {
        if (name.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(").build();
        }
        if (name.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (name.toLowerCase().contains("hitler")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("hitler not allowed, sorry :(").build();
        }
        try {
            chatService.login(name);
        } catch (ChatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        List<User> all = chatService.getOnline();
        return Response.ok(String.join("\n", all.stream().map(User::getLogin).collect(Collectors.toList()))).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/say")
    public Response say(@QueryParam("name") String name, @FormParam("msg") String msg) {
        if (name == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Name not provided").build();
        }
        if (msg == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No message provided").build();
        }
        if (msg.length() > 140) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long message").build();
        }

        try {
            chatService.say(name, msg);
        } catch (ChatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        log.info("[" + name + "]: " + msg);

        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat() {
        List<Message> chatHistory = chatService.viewChat();
        return Response.ok(String.join("\n", chatHistory
                .stream()
                .map(m -> "[" + m.getUser().getLogin() + "]: " + m.getValue())
                .collect(Collectors.toList()))).build();
    }

    //TODO implement logout here from scratch
}