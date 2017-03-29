package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/")
public class ChatResource {
    private static final Logger log = LogManager.getLogger(ChatResource.class);

    private static final TokenList tokenList = new TokenList();
    private static final ConcurrentHashMap<String, User> registeredUsers = new ConcurrentHashMap<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();

    public static User getUser(String name) {
        return registeredUsers.get(name);
    }

    public boolean authorized(String token) {
        return token != null && tokenList.isValid(token);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@QueryParam("name") String name, @QueryParam("pass") String pass) {
        if (name == null || pass == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(")
                .build();
        }
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(")
                .build();
        }
        if (!registeredUsers.containsKey(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You have to register first")
                .build();
        }
        if (registeredUsers.containsKey(name) && !pass.equals(registeredUsers.get(name).password)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect password")
                .build();
        }

        if (tokenList.isValid(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logged in").build();
        }
        Token token = new Token(name);
        tokenList.addToken(token);
        NewCookie cookie = new NewCookie("token", token.getTokenName(), "/", "", "SessionToken",
            1000, false);
        log.info("[" + name + "] logged in");
        chat.add("[" + name + "] joined");
        return Response.ok("").cookie(cookie).build();
    }

    private NewCookie login0(String name, String pass) {

        Token token = new Token(name);
        tokenList.addToken(token);
        log.info("[" + name + "] logged in");
        chat.add("[" + name + "] joined");
        return new NewCookie("token", token.getTokenName(), "/", "", "SessionToken", 1000, false);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("name") String name, @FormParam("pass1") String pass1,
        @FormParam("pass2") String pass2) {
        if (name == null || pass1 == null || pass2 == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(")
                .build();
        }
        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(")
                .build();
        }
        if (registeredUsers.contains(name)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("This nickname has already been taken").build();
        }
        if (!pass1.equals(pass2)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Reentered password is not the same").build();
        }
        log.info("[" + name + "] registered");
        registeredUsers.put(name, new User(name, pass1));
        NewCookie cookie = login0(name, pass1);
        return Response.seeOther(URI.create("/")).cookie(cookie).build();
    }

    @GET
    @Produces("text/plain")
    @Path("/online")
    public Response online(@CookieParam("token") String token) {
        if (authorized(token)) {
            return Response.ok(String.join("\n", tokenList.getAllNames())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logged in").build();
        }
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/say")
    public Response say(@FormParam("msg") String msg, @CookieParam("token") String token) {
        if (!authorized(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logged in").build();
        }
        if (msg == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No message provided")
                .build();
        }
        if (msg.length() > 140) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long message").build();
        }
        LocalDateTime ldt = LocalDateTime.now();
        String name = tokenList.getUserByToken(token).getName();
        log.info("[" + name + "]: " + msg);
        chat.add("[ (" + ldt.getHour() + ":" + ldt.getMinute() + ") " + name + "]: " + msg);
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/chat")
    public Response chat(@CookieParam("token") String token) {
        if (authorized(token)) {
            return Response.ok(String.join("\n", chat)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logged in").build();
        }

    }

    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@CookieParam("token") String token) {
        User user = tokenList.getUserByToken(token);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logged in").build();
        }
        log.info("[" + user.getName() + "] logged out");
        chat.add("[" + user.getName() + "] left");
        tokenList.deleteToken(token);
        return Response.seeOther(URI.create("/")).build();
    }
}