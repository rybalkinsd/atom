package model.server.auth;

import model.data.Token;
import model.data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by artem on 05.11.16.
 */
@Path("profile")
public class Profile {

    @Authorized
    @POST
    @Path("name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response newname(@HeaderParam("Authorization") String rawToken,@FormParam("name") String name) {
        try {
            Token token = new Token();
            token.setId(Integer.parseInt(rawToken.substring("Bearer".length()).trim()));

            if (name == null || name.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                User user = Functional.getUser(token);
                Functional.userDao.delete(user);
                user.setName(name);
                Functional.userDao.insert(user);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    @Authorized
    @POST
    @Path("password")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response newpassword(@HeaderParam("Authorization") String rawToken,@FormParam("password") String password) {
        try {
            Token token = new Token();
            token.setId(Integer.parseInt(rawToken.substring("Bearer".length()).trim()));

            if (password == null || password.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                User user = Functional.getUser(token);
                Functional.userDao.delete(user);
                user.setPassword(password);
                Functional.userDao.insert(user);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    @Authorized
    @POST
    @Path("email")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@HeaderParam("Authorization") String rawToken,@FormParam("email") String email) {
        try {
            Token token = new Token();
            token.setId(Integer.parseInt(rawToken.substring("Bearer".length()).trim()));

            if (email == null || email.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (Functional.getUser(token) == null) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            } else {
                User user = Functional.getUser(token);
                Functional.userDao.delete(user);
                user.setMail(email);
                Functional.userDao.insert(user);
            }
            return Response.ok().build();
        }
        catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }
}
