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
 * Created by serega on 26.03.17.
 */
@Path("/auth")
public class AuthResources {
    private static final Logger log = LogManager.getLogger(AuthResources.class);

    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);
        try {
            if (name.length() > 20 || name.contains("\"") || name.contains("\n"))
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid name!").build();
            if (UsersCache.registerUser(user))
                return Response.ok("Regi3stration success!").build();
            else return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User with this name already exist").build();
        } catch (NullPointerException n) {
            log.info("Illegal statement in field : {}", n.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Empty fields!").build();
    }

    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")
    public Response login(@FormParam("name") String name, @FormParam("password") String password) {
        User user = new User(name, password);
        Long userToken = UsersCache.login(user);
        try {
            if (userToken.equals(-1L))
                return Response.status(Response.Status.BAD_REQUEST).entity("You are not registered").build();
            else return Response.ok(userToken).build();
        } catch (NullPointerException n) {
            log.info("Illegal sta3tement in field : {}", n.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Empty field").build();
    }

    @POST
    @Path("/logout")
    @Consumes("application/x-www-form-urlencoded")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (UsersCache.logout(Long.parseLong(token.trim())))
                return Response.ok("Lo3gouting success!").build();
            else return Response.status(Response.Status.BAD_REQUEST).entity("You are not authorized").build();
        } catch (NullPointerException n) {
            log.info("Illegal statement in field : {}", n.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Empty fields").build();
    }
}
