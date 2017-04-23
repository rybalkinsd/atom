package dbhackaton.services;

/**
 * Created by ilnur on 12.04.17.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.FormParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import dbhackaton.Services;

@Path("/")
public class AuthenticationServlet {
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static final Services services = new Services();




    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("user") String user, @FormParam("password") String password) {
        String hashPassword = services.hashPass(password);
        if (user.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(").build();
        }
        if (user.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        if (user.toLowerCase().contains("hitler")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("hitler not allowed, sorry :(").build();
        }
        try {
            services.register(user, hashPassword);
        } catch (LoginException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("user") String name,
                          @FormParam("password") String password) {
        String hashPassword = services.hashPass(password);
        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            services.login(name, hashPassword);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        log.info("User logined");

        return Response.ok().build();
    }


    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    public Response logout(@FormParam("user") String name) {

        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            services.logout(name);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        log.info("User logouted");

        return Response.ok().build();
    }
}

  /*  @Authorized
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


