package ru.atom;

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
@Path("/auth")
public class AuthResources {
    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);

        if (UsersCache.registerUser(user)) {
            return Response.ok("Registration success!").build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Some problems with registration").build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);
        Long userToken = UsersCache.login(user);
        if (userToken.equals(-1L))
            return Response.status(Response.Status.BAD_REQUEST).entity("Problems with login").build();
        return Response.ok(userToken).build();
    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (UsersCache.logout(Long.parseLong(token.trim()))) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Some problems with logout").build();
    }
}
