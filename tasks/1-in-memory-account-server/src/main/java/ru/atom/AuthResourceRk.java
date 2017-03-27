package ru.atom;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by pavel on 24.03.17.
 */
@Path("/auth")
public class AuthResourceRk {

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("user") String userName, @FormParam("password") String password) {
        UserRk newUser = new UserRk(userName, password);

        if (UserContainerRk.registerUser(newUser)) {
            return Response.ok("Registration success!").build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Some problems with registration").build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("user") String userName, @FormParam("password") String password) {
        UserRk loginUser = new UserRk(userName, password);
        Long userTocken = UserContainerRk.login(loginUser);

        if (userTocken.equals(-1L)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Problems with login").build();
        }

        return Response.ok(userTocken).build();
    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    @AuthorizedRk
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tocken) {
        if (UserContainerRk.logout(Long.parseLong(tocken.trim()))) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Some problems with logout").build();
    }

}
