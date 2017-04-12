package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dao.UserDao;
import ru.atom.server.Authorized;
import ru.atom.StorageToken;
import ru.atom.object.Token;
import ru.atom.object.User;
import ru.atom.Users;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Date;


/**
 * Created by Fella on 28.03.2017.
 */
@Path("/*/")
public class RegisterJersey {
    private static final Logger log = LogManager.getLogger(RegisterJersey .class);
    private static final UserDao userDao = new UserDao();


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("register")
    @Produces("text/plain")
    public Response register(@FormParam("user") String login,
                             @FormParam("password") String password) {

        if (login.length() > 20) {
            log.info("This login too long.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Sorry, your login too long )= ").build();
        }

        if (password.length() > 20) {
            log.info("This password too long.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Sorry, your password too long )= ").build();
        }

        /*if (Users.isContainsName(login)) {
            log.info("this login is busy.");
            return Response.status(Response.Status.UNAUTHORIZED).entity("Sorry, this login is busy").build();
        }*/



        User newUser = new User( login, password, new Date(System.currentTimeMillis()));
       /* log.info("New user login " + Users.getUser(login).getLogin().toString());*/
        userDao.insert(newUser);
        log.info("[" + login + "] logined");
        return Response.ok("Congratulations, you are registered").build();





           /* List<User> alreadyLogined = userDao.getAllWhere("chat.user.login = '" + name + "'");
            if (alreadyLogined.stream().anyMatch(l -> l.getLogin().equals(name))) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Already logined").build();
            }
            */




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







