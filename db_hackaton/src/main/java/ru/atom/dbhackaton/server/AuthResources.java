package ru.atom.dbhackaton.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ru.atom.dbhackaton.exeptions.RegisterExeption;
import ru.atom.dbhackaton.server.services.Services;

import static ru.atom.dbhackaton.MyLogger.getLog;
import static ru.atom.dbhackaton.WorkWithProperties.getStrBundle;

/**
 * Class provides auth operations.
 * @author Western-Co
 */
@Path("")
public class AuthResources {
    private static final Services services = new Services();

    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Path("/register")
    public Response register(@FormParam("user") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("miss.param")).build();
        } else if (name.length() < 1) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.short")).build();
        } else if (name.length() > 20) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.long")).build();
        } else {
            try {
                services.registerUser(name, password);
            } catch (RegisterExeption registerExeption) {
                getLog().error(registerExeption.getMessage());
                return Response
                        .status(Status.BAD_REQUEST)
                        .entity(getStrBundle()
                        .getString("already.registered"))
                        .build();
            }
        }
        return Response.ok(getStrBundle().getString("registered")).build();
    }

    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Path("/login")
    public Response login(@FormParam("user") String name,
                          @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("miss.param")).build();
        } else if (name.length() < 1) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.short")).build();
        } else if (name.length() > 20) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.long")).build();
        } else {
            try {
                return Response.ok(services.loginUser(name, password)).build();
            } catch (RegisterExeption registerExeption) {
                getLog().error(registerExeption.getMessage());
                return Response.status(Status.BAD_REQUEST).entity(registerExeption.getMessage()).build();
            }
        }
    }

    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Path("/logout")
    // Проверяем есть ли header и берем его значение
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token != null) {
            token = token.substring("Bearer ".length());
            try {
                services.logoutUser(token);
            } catch (RegisterExeption registerExeption) {
                getLog().error(registerExeption.getMessage());
                return Response.status(Status.BAD_REQUEST).entity(registerExeption.getMessage()).build();
            }
            getLog().info(getStrBundle().getString("logout.ok"));
            return Response.ok(getStrBundle().getString("logout.ok")).build();
        } else
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("logout.error")).build();
    }
}
