package ru.atom.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by konstantin on 25.03.17.
 */
@Path("/auth")
public class AuthService {

    @POST
    @Path("/register")
    @Produces("text/plain")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("name") String user, @FormParam("password") String password) {
        User newUser = new User(user, password);

        if (UserContainer.registerUser(newUser))
            return Response.ok("Registration success").build();
        return Response.status(Response.Status.BAD_REQUEST).entity("Problems with registration").build();
    }

    @POST
    @Path("/login")
    @Produces("text/plain")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("name") String user, @FormParam("password") String password) {
        User loginUser = new User(user, password);
        Long userTocken = UserContainer.login(loginUser);

        if (userTocken.equals(-1L))
            return Response.status(Response.Status.BAD_REQUEST).entity("Problems with login").build();
        return Response.ok(userTocken).build();
    }

    @Authorized
    @POST
    @Path("/logout")
    @Produces("text/plain")
    @Consumes("application/x-www-form-urlencoded")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tocken) {
        if (UserContainer.logout(Long.parseLong(tocken.trim())))
            return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).entity("Problems with logout").build();
    }
}
