package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;
import sun.rmi.runtime.Log;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Path("/")
public class Service {
    private static final Logger log = LogManager.getLogger(Service.class);

    private static ConcurrentHashMap<String, User> registerToUser = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, User> loginToUser = new ConcurrentHashMap<>();
    private static TokenHolder tokenHolder = new TokenHolder();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/register")
    public Response register(@FormParam("name") String name, @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect name or password :(").build();
        }
        if (registerToUser.containsKey(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Duplicated login : (").build();
        }
        User user = new User(name, password);
        registerToUser.put(name, user);
        log.info("[" + name + "] registered");
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/login")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);
        if (!registerToUser.containsValue(user)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect name or password :(").build();
        }
        if (loginToUser.containsValue(user)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logged in:(").build();
        }
        if (!tokenHolder.isValid(name)) {
            return Response.ok(tokenHolder.getToken(name).toString()).build();
        }
        Token token = new Token();
        while (!tokenHolder.isValid(token)) {
            token = new Token();
        }
        loginToUser.put(name, user);
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
        String login = tokenHolder.removeToken(token);
        log.info("[" + login + "] logged out");
        return Response.ok().build();
    }

    @GET
    @Produces("text/plain")
    @Path("/data/users")
    public Response userList() {
        ArrayList<String> users = tokenHolder.loginedUsers();
        String response = "{Users: [" + String.join("}{", users) + "}]}";
        return Response.ok(response).build();
    }
}
