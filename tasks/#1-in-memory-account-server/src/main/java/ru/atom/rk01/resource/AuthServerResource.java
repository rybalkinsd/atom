package ru.atom.rk01.resource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import ru.atom.rk01.Token;
import ru.atom.rk01.UserManager;
import ru.atom.rk01.User;
import ru.atom.rk01.AuthToken;
import ru.atom.rk01.Authorized;

/**
 * Created by dmbragin on 3/28/17.
 */
@Path("/auth/")
public class AuthServerResource {
    private static final Logger log = LogManager.getLogger(AuthServerResource.class);
    private static final UserManager userManager = UserManager.getInstance();

    @POST
    @Path("register/")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String login, @FormParam("passwd") String passwd) {
        if (login == null) {
            log.info("Url register/ login {}; password {}; status {}", login, passwd, String.valueOf(400));
            return Response.status(Response.Status.BAD_REQUEST).entity("No login provided").build();
        }
        if (passwd == null) {
            log.info("Url register/ login {}; password {}; status {}", login, passwd, String.valueOf(400));
            return Response.status(Response.Status.BAD_REQUEST).entity("No password provided").build();
        }

        User newUser = new User(login, passwd);
        boolean isRegistered = userManager.register(newUser);
        if (isRegistered) {
            log.info("Url register/ login {}; password {}; status {}", login, passwd, String.valueOf(200));
            return Response.ok("You are registered").build();
        } else {
            log.info("Url register/ login {}; password {}; status {}", login, passwd, String.valueOf(400));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User with this name can not be register")
                    .build();
        }
    }

    @POST
    @Path("login/")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("login") String login, @FormParam("passwd") String passwd) {
        if (login == null) {
            log.info("Url login/ login {}; password {}; status {}", login, passwd, String.valueOf(400));
            return Response.status(Response.Status.BAD_REQUEST).entity("No login provided").build();
        }
        if (passwd == null) {
            log.info("Url login/ login {}; password {}; status {}", login, passwd, String.valueOf(400));
            return Response.status(Response.Status.BAD_REQUEST).entity("No password provided").build();
        }

        User user = new User(login, passwd);
        Token token = userManager.login(user);
        if (token != null) {
            log.info("Url register/ login {}; password {}; status {}", login, passwd, String.valueOf(200));
            return Response.ok(token.getTokenString()).build();
        } else {
            log.info("Url register/ login {}; password {}; status {}", login, passwd, String.valueOf(400));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User with this name can not be login")
                    .build();
        }
    }

    @Authorized
    @POST
    @Path("logout/")
    @Produces("text/plain")
    public Response login(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenString) {
        Token token = new AuthToken();
        token.setTokenString(tokenString);
        userManager.logout(token); // == True, because of @Authorized
        log.info("Url register/ token {}; status {}",token.getTokenString(), String.valueOf(400));
        return Response.ok("Logout").build();
    }


}
