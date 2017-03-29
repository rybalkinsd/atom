package ru.atom;

/**
 * Created by user on 28.03.2017.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/")
public class ServerResource {
    private static final Logger log = LogManager.getLogger(ServerResource.class);

    private static final ArrayList<User> registered = new ArrayList<>();
    private static final ArrayList<String> logines = new ArrayList<>();
    private static final ArrayList<Token> tokens = new ArrayList<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/register")
    public Response register(@FormParam("login") String login, @FormParam("password") String password) {
        if (logines.contains(login)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("login in usage").build();
        }
        if (login.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        User user = new User(login, password);
        log.info("[" + login + "] registered");
        registered.add(user);
        logines.add(login);
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/login")
    public Response login(@FormParam("login") String login, @FormParam("password") String password) {
        for (User user: registered) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                for (Token tok: tokens) {
                    if (tok.getUser().equals(user)) {
                        log.info("[" + login + "] has already been logined");
                        return Response.ok(tok.getToken()).build();
                    }
                }
                Token token = new Token(user);
                tokens.add(token);
                log.info("[" + login + "] logined");
                return Response.ok(token.getToken()).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("You are not registered yet").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/logout")
    public Response logout(@FormParam("token") long token) {
        for (Token tok:tokens) {
            if (tok.getToken() == token) {
                log.info("[" + tok.getUser().getLogin() + "] logout successful");
                tokens.remove(tok);
                return Response.ok().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("There is no such logined user").build();
    }

    @GET
    @Produces("application/json")
    @Path("/data/users")
    public Response online() {
        JSONArray ar = new JSONArray();
        JSONObject resultJson = new JSONObject();
        for (Token tok:tokens) {
            ar.add(tok.getUser().getLogin() + " " + tok.getUser().getPassword() + " : " + tok.getToken());
        }
        resultJson.put("users", ar);
        log.info("request completed");
        return Response.ok(resultJson.toJSONString()).build();
    }

}
