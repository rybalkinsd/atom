package accountserver.api;

import accountserver.AccountService;
import accountserver.model.data.Token;
import accountserver.model.response.ApiErrors.LoginExistsError;
import accountserver.model.response.ApiErrors.WrongCredentialsError;
import accountserver.model.response.ApiRequestError;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by eugene on 10/13/16.
 */
@Path("/auth")
@Produces("text/plain")
@Consumes("application/x-www-form-urlencoded")
public class Auth {
    private static final AccountService accountService = new AccountService();

    @POST
    @Path("login")
    public Response signIn(
            @DefaultValue("") @FormParam("user") String login,
            @DefaultValue("") @FormParam("password") String password
    ){
        Token token;
        try {
            token = accountService.signIn(login, password);
        }
        catch (WrongCredentialsError e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        catch (ApiRequestError error) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(token.toString()).build();
    }

    @POST
    @Path("register")
    public Response signUp(
            @DefaultValue("") @FormParam("user") String login,
            @DefaultValue("") @FormParam("password") String password
    ){
        try {
            accountService.signUp(login,password);
        }
        catch (LoginExistsError e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        catch (ApiRequestError apiRequestError) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok("User " + login + " registered.").build();
    }
}

