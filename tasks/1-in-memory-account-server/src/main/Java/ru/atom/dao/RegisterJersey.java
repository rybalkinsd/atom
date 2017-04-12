package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.authfilter.Authorized;
import ru.atom.StorageToken;
import ru.atom.Token;
import ru.atom.User;
import ru.atom.Users;

import ru.atom.dao.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


/**
 * Created by Fella on 28.03.2017.
 */
@Path("auth")
public class RegisterJersey {
    private static final Logger log = LogManager.getLogger(RegisterJersey .class);


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("register")
    @Produces("text/plain")
    public Response register(@FormParam("user") String login,
                             @FormParam("password") String password) {

        if (login.length() > 30) {
            log.info("This login too long.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Sorry, your login too long )= ").build();
        }

        if (Users.isContainsName(login)) {
            log.info("this login is busy.");
            return Response.status(Response.Status.UNAUTHORIZED).entity("Sorry, this login is busy").build();
        }

        final String findByNameCondition = "name=\'" + login + "\'";

        if (DatabaseClass.checkByCondition(findByNameCondition)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .build();
        }

        Users.put(new User(login, password));
        DatabaseClass.insertUser(Users.getUser(login));

        log.info("New user login = " + login  + ", password = " + password);
        //Users.put(new User(login, password));
        log.info("Useres " + Users.getUser(login).getLogin().toString());
        return Response.ok("Congratulations, you are registered").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("login")
    @Produces("text/plain")
    public Response login(@FormParam("user") String login,
                           @FormParam("password") String password) {

        log.info("user= " + login + ", password" + password);
        if (!Users.isContainsName(login)) {
            log.info("wrong login");
            return Response.status(Response.Status.BAD_REQUEST).entity("wrong login").build();
        }
        if (!Users.getUserPsword(login).equals(password)) {
            log.info("wrong password");
            return Response.status(Response.Status.BAD_REQUEST).entity("wrong password").build();
        }

        User romashka = Users.getUser(login);
        Token yourToken;
        if (StorageToken.isContainsUser(romashka)) {
            yourToken = StorageToken.getTokenSt(romashka);
        } else {
            yourToken = Token.createToken();
            StorageToken.add(yourToken,romashka);
        }

        log.info(yourToken.toString());
        return Response.ok(yourToken.toString()).build();

    }


    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) throws Exception {
        Response response;
        try {
            User user = StorageToken.getUserSt(Token.getTokenfromString(tokenParam));
            Token.getTokenfromString(tokenParam).deleteToken();
            response = Response.ok("User{" + user.getLogin().toString() + "} logout.").build();
        }  catch (Exception e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
        }
        return response;
    }
}







