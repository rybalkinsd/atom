package ru.atom.lecture06.server.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import org.w3c.dom.UserDataHandler;
import ru.atom.lecture06.server.dao.MessageDao;
import ru.atom.lecture06.server.dao.UserDao;
import ru.atom.lecture06.server.model.Message;
import ru.atom.lecture06.server.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final UserDao userDao = new UserDao();
    private static final MessageDao messageDao = new MessageDao();


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
        List<User> alreadyLogined = userDao.getAllWhere("chat.user.login = '" + name + "'");
        if (alreadyLogined.stream().anyMatch(l -> l.getLogin().equals(name))) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        User newUser = new User().setLogin(name);
        log.info("[" + name + "] logined");

        //TODO send message "[user]: joined"
        userDao.insert(newUser);
        User user = userDao.getByName(name);
        Message newMessage = new Message().setUser(user).setValue("joined").setTimestamp(new Date());
        messageDao.insert(newMessage);

        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online() {
        List<User> all = userDao.getAll();
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

        List<User> authors = userDao.getAllWhere("chat.user.login = '" + name + "'");
        if (authors.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logined").build();
        }
        User author = authors.get(0);

        Message message = new Message()
                .setUser(author)
                .setValue(msg);

        messageDao.insert(message);
        log.info("[" + name + "]: " + msg);

        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat(@QueryParam("name") String name) {
        if (name == null || name.isEmpty()) {
            List<Message> chatHistory = messageDao.getAll();
            return Response.ok(String.join("\n", chatHistory
                    .stream()
                    .map(m -> "[" + m.getUser().getLogin() + "]: " + m.getValue())
                    .collect(Collectors.toList()))).build();
        }

        User user = userDao.getByName(name);

        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No such logined user " + name).build();
        }

        List<Message> chatHistory = messageDao.getAll();
        return Response.ok(String.join("\n", chatHistory
                .stream()
                .map(m -> "[" + m.getUser() + "]: " + m.getValue())
                .collect(Collectors.toList()))).build();
    }
}