package model.server.auth;

import model.data.Match;
import model.data.Token;
import model.data.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by artem on 05.11.16.
 */
@Path("profile")
public class Profile {
    private static final Logger log = LogManager.getLogger(Profile.class);
    //{"id":111,"date":1478464263086}
    // curl -i -X POST -H "Authorization: Bearer{\"id\":115,\"date\":1478464669110}" -H "Host: localhost:8080" -d "name=3" "http://localhost:8080/profile/name"

    @Authorized
    @POST
    @Path("name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response newname(@HeaderParam("Authorization") String rawToken,@FormParam("name") String name) {
        try {
            Token token = Functional.mapper.readValue(rawToken.substring("Bearer".length()).trim(), Token.class);

            if (name == null || name.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            User user2 = Functional.getAssertUser(name, null);

            if (user2 != null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                User user = Functional.getUser(token);
                Functional.userDao.delete(user);
                Functional.matchDao.delete(user);
                user.setName(name);
                Functional.userDao.insert(user);
                Match match = new Match().setToken(token.getId()).setUser(user.getId());
                Functional.matchDao.insert(match);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }
    // curl -i -X POST -H "Authorization: Bearer{\"id\":106,\"date\":1478463636674}" -H "Host: localhost:8080" -d "password=3" "http://localhost:8080/profile/password"

    @Authorized
    @POST
    @Path("password")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response newpassword(@HeaderParam("Authorization") String rawToken,@FormParam("password") String password) {
        try {
            Token token = Functional.mapper.readValue(rawToken.substring("Bearer".length()).trim(), Token.class);

            if (password == null || password.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                User user = Functional.getUser(token);
                Functional.userDao.delete(user);
                Functional.matchDao.delete(user);
                user.setPassword(password);
                Functional.userDao.insert(user);
                Match match = new Match().setToken(token.getId()).setUser(user.getId());
                Functional.matchDao.insert(match);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }
    // curl -i -X POST -H "Authorization: Bearer{\"id\":106,\"date\":1478463636674}" -H "Host: localhost:8080" -d "email=sdnyuhoybatya" "http://localhost:8080/profile/email"

    @Authorized
    @POST
    @Path("email")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@HeaderParam("Authorization") String rawToken,@FormParam("email") String email) {
        try {
            Token token = Functional.mapper.readValue(rawToken.substring("Bearer".length()).trim(), Token.class);

            if (email == null || email.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                User user = Functional.getUser(token);
                Functional.userDao.delete(user);
                Functional.matchDao.delete(user);
                user.setMail(email);
                Functional.userDao.insert(user);
                Match match = new Match().setToken(token.getId()).setUser(user.getId());
                Functional.matchDao.insert(match);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }
}
