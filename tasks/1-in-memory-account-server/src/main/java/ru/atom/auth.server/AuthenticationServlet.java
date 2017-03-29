package ru.atom.auth.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.atom.model.Token;
import ru.atom.model.TokenMap;
import ru.atom.model.User;
import ru.atom.model.UserMap;

@Path("/")
public class AuthenticationServlet {
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static UserMap userMap = new UserMap();
    private static TokenMap tokenMap = new TokenMap();

    @POST
    @Path("auth/register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("name") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = new User(name);

        if (!userMap.putUser(user, password)) {
            log.info("Attempt for registering dublicate user: " + user.getName());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user with name " + user.getName() + " registered");
        return Response.ok("User " + user.getName() + " registered.").build();
    }

    @POST
    @Path("auth/login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("name") String name,
                          @FormParam("password") String password) {

        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = new User(name);

        try {
            if (!userMap.authenticate(user, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            Token token = tokenMap.issueToken(user, tokenMap);

            return Response.ok(Long.toString(token.getId())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Authorized
    @POST
    @Path("auth/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logout() {
        Token token = AuthenticationFilter.getToken();

        try {
            log.info("User " + tokenMap.getUser(token).getName() + " logout");
            tokenMap.removeToken(token);
            return Response.ok("You have logout").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Authorized
    @Path("data/users")
    @GET
    public Response users() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(tokenMap.loginedUsers());
        json = "{users:" + json + "}";
        log.info("GSON :" + json);
        return Response.ok(json).build();
    }
}
