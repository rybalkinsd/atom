package model.server.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.MatchDao;
import model.dao.TokenDao;
import  model.dao.UserDao;
import model.data.Match;
import model.data.Token;
import  model.data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/auth")
public class Authentication {


    //curl -i -X POST -H "Contion/x-www-form-urlencoded" -H "Host: localhost:8080" -d "user=1&password=1" "http://localhost:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String name,
                             @FormParam("password") String password) {


        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            User user = Functional.getAssertUser(name, null);

            if (user != null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

            user = new User();

            user.setName(name).setPassword(password);
            Functional.userDao.insert(user);
            LeaderBoardProvider.addRecord(user.getId());

            return Response.ok("User " + user.getName() + " registered.").build();
        }catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    //curl -i -X POST -H "Contion/x-www-form-urlencoded" -H "Host: localhost:8080" -d "user=1&password=1" "http://localhost:8080/auth/login"
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("user") String name,
                             @FormParam("password") String password) {

        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            User user = Functional.getAssertUser(name, password);
            if (user == null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

            Token token = Functional.getAssertToken(user);

            String tokenJson = Functional.mapper.writeValueAsString(token);

            return Response.ok(tokenJson).build();
        }catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }


    // curl -i -X POST -H "Authorization: Bearer{"id":83,"date":1478277630646}" -H "Host: localhost:8080" "http://localhost:8080/auth/logout"
    @Authorized
    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logoutUser(@HeaderParam("Authorization") String rawToken) {
        try {
            Token token = new Token();
            token.setId(Integer.parseInt(rawToken.substring("Bearer".length()).trim()));
            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                Functional.matchDao.delete(token);
                Functional.tokenDao.delete(token);
                return Response.ok().build();
            }
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
