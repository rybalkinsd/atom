package ru.atom.dbhackaton.server.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.Authorized;
import ru.atom.dbhackaton.server.service.AuthException;
import ru.atom.dbhackaton.server.service.AuthService;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

/**
 * Created by serega on 26.03.17.
 */
@Path("/")
public class AuthResources {
    private static final Logger log = LogManager.getLogger(AuthResources.class);

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("user") String name, @FormParam("password") String password) {
        try {
            AuthService.register(name, password);
        } catch (AuthException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Success").build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("user") String name, @FormParam("password") String password) {
        try {
            AuthService.login(name, password);
        } catch (AuthException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Success").build();
    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) Long token) {
        try {
            AuthService.logout(token);
        } catch (AuthException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("logout success").build();
    }
}
