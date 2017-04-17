package ru.atom.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dao.DatabaseClass;
import ru.atom.object.Token;
import ru.atom.object.User;


import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Date;






/**
 * Created by Fella on 28.03.2017.
 */
@Path("/")
public class RegisterJersey {
    private static final Logger log = LogManager.getLogger(RegisterJersey.class);

    DatabaseClass dbclass =new DatabaseClass();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("register")
    @Produces("text/plain")
    public Response register(@FormParam("user") String login,
                             @FormParam("password") String password) {


       if (login == null || password == null) {
            log.info("Не заполненые поля");
            return Response.status(Response.Status.BAD_REQUEST).entity("You must write in login and password").build();
        }


        if (login.length() > 20) {
            log.info("This login too long.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Sorry, your login too long )= ").build();
        }

        if (password.length() > 20) {
            log.info("This password too long.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Sorry, your password too long )= ").build();
        }


        if (dbclass.checkByConditionUser("login = \'" + login + "\'")) {
            log.info("Already registered!");
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }
        User newUser = new User()
                .setLogin(login)
                .setPassword(password)
                .setRegistrationDate(new Date(System.currentTimeMillis()));
        dbclass.insertUser(newUser);
        log.info("New user registr [" + login + "]");
        return Response.ok().build();
    }


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("login")
    @Produces("text/plain")
    public Response login(@FormParam("user") String login,
                          @FormParam("password") String password) {

        log.info("user=" + login + ", password=" + password);
        if (!dbclass.checkByConditionUser("login = \'" + login + "\'")) {
            log.info("wrong login");
            return Response.status(Response.Status.BAD_REQUEST).entity("wrong login").build();
        }
        if (!dbclass.checkByConditionUser("login = \'" + login + "\'","password = \'" + password + "\'")) {
            log.info("wrong password");
            return Response.status(Response.Status.BAD_REQUEST).entity("wrong password").build();
        }


        Token yourToken = dbclass.issueToken(login);
        log.info("New user login [" + login + "]");
        log.info(yourToken.toString());
        return Response.ok(yourToken.toString()).build();
    }


    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) {
        System.out.println(tokenParam.substring(7));

       /* if (DatabaseClass.deleteToken(tokenParam.substring(7))) {
            log.info("User logout");
            return Response.ok("User logout.").build();
        } else {
            log.info("User logout");
            return Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
        }*/
        return Response.ok().build();
    }
}







