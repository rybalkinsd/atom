package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.StorageToken;
import ru.atom.Users;
import ru.atom.dao.DatabaseClass;
import ru.atom.dao.TokenDao;
import ru.atom.dao.UserDao;
import ru.atom.object.Token;
import ru.atom.object.User;

import javax.swing.text.html.HTMLDocument;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/*import ru.atom.server.Authorized;*/


/**
 * Created by Fella on 28.03.2017.
 */
@Path("/")
public class RegisterJersey {
    private static final Logger log = LogManager.getLogger(RegisterJersey.class);
    private static final UserDao userDao = new UserDao();
    private static final TokenDao tokenDao = new TokenDao();


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

        List<User> alreadyLogined = userDao.getAllWhere("bombergirl.user.login = '" + login + "'");
        if (alreadyLogined.stream().anyMatch(l -> l.getLogin().equals(login))) {
            log.info("Already registered!");
            return Response.status(Response.Status.BAD_REQUEST).entity("Already registered").build();
        }
        User newUser = new User()
                .setLogin(login)
                .setPassword(password)
                .setRegistrationDate(new Date(System.currentTimeMillis()));
        userDao.insert(newUser);
        log.info("New user registr [" + login + "]");
        return Response.ok().build();
    }






    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("login")
    @Produces("text/plain")
    public Response login(@FormParam("user") String login,
                           @FormParam("password") String password) {
        List<User> alreadyLogined = userDao.getAllWhere("bombergirl.user.login = '" + login + "'");



        log.info("user=" + login + ", password=" + password);
        if (!alreadyLogined.stream().anyMatch(l -> l.getLogin().equals(login))){
            log.info("wrong login");
            return Response.status(Response.Status.BAD_REQUEST).entity("wrong login").build();
        }
        if (!alreadyLogined.stream().anyMatch(l -> l.getPassword().equals(password))) {
            log.info("wrong password");
            return Response.status(Response.Status.BAD_REQUEST).entity("wrong password").build();
        }//Посмотреть будет ли совпадать пароль


       Token yourToken = DatabaseClass.issueToken(login);
        log.info("New user login [" + login + "]");
        log.info(yourToken.toString());
        return Response.ok(yourToken.toString()).build();

    }



    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String tokenParam) {
        System.out.println(tokenParam.substring(9));

       if (DatabaseClass.deleteToken(tokenParam.substring(9))){
           log.info("User logout");
           return Response.ok("User logout.").build();
       } else {
           log.info("User logout");
           return Response.status(Response.Status.BAD_REQUEST).entity("User isn't logouted").build();
       }
    }
}







