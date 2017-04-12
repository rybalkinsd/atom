package ru.atom.dbhackaton.server.services;

/**
 * Created by ilnur on 12.04.17.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.atom.dbhackaton.server.Authorized;
import ru.atom.dbhackaton.server.Services;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;

@Path("/")
public class AuthenticationServlet {
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static final Services services = new Services();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("user") String user, @FormParam("password") String password) {
        if (user.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(").build();
        }
        if (user.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (user.toLowerCase().contains("hitler")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("hitler not allowed, sorry :(").build();
        }
        if (password.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short pass, sorry :(").build();
        }
        if (password.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long pass, sorry :(").build();
        }
        try {
            services.register(user);
        } catch (LoginException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        return Response.ok().build();
    }
/*
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("user") String name,
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
        return Response.ok(json).build();*/

}
