package ru.atom.dbhackaton.auth.server;

/**
 * Created by ilnur on 12.04.17.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;

@Path("/")
public class AuthenticationServlet {
    private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
    private static final Services services = new Services();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("user") String user, @FormParam("password") String password) {
        Response response;
        response = services.register(user, password);

        log.info("User with name  " + user + " has registered");

        return response;
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("user") String name,
                          @FormParam("password") String password) {
        Response response;
        response = services.login(name, password);

        log.info("User with name  " + name + " has logined");

        return response;
    }

    @Authorized
    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    public Response logout(@HeaderParam("Authorization") String tokenParam) {
        Response response;
        response = services.logout(tokenParam);

        String token = tokenParam.substring("Bearer".length()).trim();
        log.info("User with token  " + token + " has logouted");

        return response;
    }
}


