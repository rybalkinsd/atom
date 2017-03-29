package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import sun.rmi.runtime.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Path("/")
public class Service {
    private static final Logger log = LogManager.getLogger(Service.class);

    private ConcurrentHashMap<String, User> loginToUser = new ConcurrentHashMap<>();
    private TokenHolder tokenHolder = new TokenHolder();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/register")
    public Response register(@FormParam("user") String name, @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect name or password :(").build();
        }
        if (loginToUser.containsKey(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Duplicate login : (").build();
        }
        User user = new User(name, password);
        loginToUser.put(name, user);
        log.info("[" + name + "] registered");
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/login")
    public Response login(@QueryParam("name") String name, @QueryParam("password") String password) {
        User user = new User(name, password);
        if (!loginToUser.containsValue(user)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect name or password :(").build();
        }
        if (!tokenHolder.isValid(name)) {
            return Response.ok(tokenHolder.getToken(name).toString()).build();
        }
        Token token = new Token();
        while (!tokenHolder.isValid(token)) {
            token = new Token();
        }
        log.info("[" + name + "] logged in");
        tokenHolder.put(token, name);
        return Response.ok(tokenHolder.getToken(name).toString()).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/logout")
    public Response logout(@HeaderParam("Authorization") String rawToken) {
        Token token = new Token(rawToken);
        if (tokenHolder.isValid(token)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect token :(").build();
        }
        log.info("[" + token + "] logged out");
        tokenHolder.removeToken(token);
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/data/users")
    public Response userList() {
        ArrayList<String> users = tokenHolder.loginedUsers();
        String s = "{Users: [" + String.join("}{", users) + "}]}";
        return Response.ok(s).build();
    }
}
