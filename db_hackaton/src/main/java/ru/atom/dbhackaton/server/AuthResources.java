package ru.atom.dbhackaton.server;

import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.HttpHeaders;
import org.hibernate.Session;
import ru.atom.dbhackaton.Token;
import ru.atom.dbhackaton.User;


import static ru.atom.dbhackaton.MyLogger.getLog;
import static ru.atom.dbhackaton.WorkWithProperties.getStrBundle;


/**
 * Class provides auth operations.
 * @author Western-Co
 */
@Path("auth")
public class AuthResources {
    public AuthResources() {}

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
            User newUser = new User(name, password);
            if (regUsers.contains(newUser)) {
                getLog().warn(getStrBundle().getString("already.registered"));
                return Response.status(Status.FORBIDDEN).entity(getStrBundle().getString("already.registered")).build();
            } else {
                regUsers.add(newUser);
                String registered = String.format(getStrBundle().getString("registered"), name);
                getLog().info(registered);
                return Response.ok(registered).build();
            }
        }
    }

    @POST
    @Produces({"text/plain"})
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
           regiterUser(name, password);
        }
    }


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    @Authorized
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
        if (token != null) {
            token = token.substring("Bearer ".length());
            Token tokenLogout = new Token(token);
            if (tokenLogout.getToken() == -1L) {
                throw new NotAllowedException(getStrBundle().getString("invalid.token"));
            }
            if (authUsers.containsKey(tokenLogout)) {
                String name = authUsers.getUser(tokenLogout).getName();
                authUsers.remove(tokenLogout);
                String logoutStr = String.format(getStrBundle().getString("logout"), name);
                getLog().info(logoutStr);
                return Response.ok(logoutStr).build();
            }
        }
        getLog().warn(getStrBundle().getString("logout.error"));
        return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("logout.error")).build();
    }
}
