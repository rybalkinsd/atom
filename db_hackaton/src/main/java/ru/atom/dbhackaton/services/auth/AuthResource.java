package ru.atom.dbhackaton.services.auth;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by BBPax on 23.03.17.
 */
@Path("/")
public class AuthResource {
    private static final Logger log = LogManager.getLogger(AuthResource.class);
    private static final AuthService authService = new AuthService();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("user") String name, @FormParam("password") String password) {
        if (name == null || name.isEmpty()) {
            log.info("Login is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Login, pls").build();
        }
        if (password == null || password.isEmpty()) {
            log.info("Password is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Password, pls").build();
        }
        if (password.length() < 5) {
            log.info("Password is too short");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password is too short. Minimal length is 5").build();
        }

        log.info("Registration of user: " + name);
        User user = new User().setLogin(name).setPassword(password);

        try {
            authService.register(user);
            log.info("New user \"" + name + "\" created");
            //users.put(name, user);
        } catch (AuthException e) {
            log.info("User \"" + name + "\" exists");
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registrated").build();
        }
        return Response.ok("ok").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("user") String name, @FormParam("password") String password) {
        if (name == null || name.isEmpty()) {
            log.info("Login is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Login, pls").build();
        }
        if (password == null || password.isEmpty()) {
            log.info("Password is empty");
            return Response.status(Response.Status.BAD_REQUEST).entity("Enter Password, pls").build();
        }
        log.info("Login user " + name);
        Token token;
        try {
            token = authService.login(name, password);
            if (token == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid password for user " + name).build();
            }
        } catch (AuthException a) {
            log.info("User " + name + " not found");
            return Response.status(Response.Status.BAD_REQUEST).entity("User " + name + " not found").build();
        }
        return  Response.ok(token.getToken().toString()).build();
    }

    @Authorized
    @POST
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) {
        Long token = Long.parseLong(tokenParam.substring("Bearer".length()).trim());
        log.info("Logout with token " + token);
        try {
            authService.logout(token);
        } catch (AuthException a) {
            log.info("User with token " + token + " is not logined");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("User with token " + token + " is not logined").build();
        }
        return Response.ok().build();
    }
}
