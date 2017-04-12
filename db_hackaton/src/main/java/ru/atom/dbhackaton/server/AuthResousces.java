package ru.atom.dbhackaton.server;

import ru.atom.dbhackaton.server.service.UserService;
import ru.atom.dbhackaton.server.service.UserException;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by salvador on 12.04.17.
 */

@Path("/")
public class AuthResousces {
    private static final UserService USER_SERVICE = new UserService();
    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("user") String userName, @FormParam("password") String password) {

        if (userName.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(").build();
        }
        if (userName.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        try {

            USER_SERVICE.register(userName, password);
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("user") String userName, @FormParam("password") String password) {
        long token = 0;
        if (userName.length() < 1) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(").build();
        }
        if (userName.length() > 20) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(").build();
        }
        try {

            token = USER_SERVICE.login(userName, password);
        } catch (UserException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad name or password").build();
        }
        return Response.ok(token).build();
    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) long tocken) {
        if (USER_SERVICE.logout(tocken)) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Some problems with logout").build();
    }
}
