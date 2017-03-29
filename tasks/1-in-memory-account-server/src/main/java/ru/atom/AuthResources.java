package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

/**
 * Created by alex on 28.03.17.
 */
@Path("/auth")
public class AuthResources {
    private static Logger log = LogManager.getLogger(AuthResources.class);


    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);
        try {
            if (StorageOfUsers.registerUser(user))
                return Response.ok("Registration success!").build();
            else return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User with this name already exist").build();
        } catch (NullPointerException e) {
            log.info("Field is {}", e.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Field is Null").build();

    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);
        Long userToken = StorageOfUsers.login(user);
        if (userToken.equals(-1L))
            return Response.status(Response.Status.BAD_REQUEST).entity("Problems with login").build();
        return Response.ok(userToken).build();
    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (StorageOfUsers.logout(Long.parseLong(token.trim()))) {
            return Response.ok("Logout successful").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Some problems with logout").build();
    }
}
