package ru.atom.dbhackaton.server.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.dao.TokenDao;
import ru.atom.dbhackaton.server.service.AuthService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


@Path("/")
public class AuthResource {
    private static final Logger logger = LogManager.getLogger(AuthResource.class);

    private static final int MIN_USER_NAME_LEN = 1;
    private static final int MAX_USER_NAME_LEN = 21;
    private static final int MIN_PASSWORD_LEN = 1;
    private static final int MAX_PASSWORD_LEN = 21;
    private static AuthService authService = new AuthService();


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    @Produces("text/plain")
    public Response register(@FormParam("user") String userName, @FormParam("password") String password) {
        if (userName == null || password == null)
            return Response.status(Response.Status.LENGTH_REQUIRED).entity("Invalid request").build();

        if (checkNameLength(userName)) {
            Response response = Response.status(Response.Status.LENGTH_REQUIRED)
                    .entity("Неверный формат имени пользователя!").build();
            return response;
        }
        if (checkPasswordLength(password)) {
            return Response.status(Response.Status.LENGTH_REQUIRED).entity("Неверный формат пароля!").build();
        }
        try {
            authService.register(userName, password);
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Возникла ошибка, попробуйте еще раз").build();
        }
        logger.info("Пользователь {} успешно зарегистрирован", userName);
        return Response.ok().entity("[" + userName + "] успешно зарегистрирован").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register/check")
    @Produces("text/plain")
    public Response registerCheck(@FormParam("user") String userName) {
        if (userName == null)
            return Response.status(Response.Status.LENGTH_REQUIRED).entity("Invalid request").build();

        if (checkNameLength(userName)) {
            Response response = Response.status(Response.Status.LENGTH_REQUIRED)
                    .entity("Неверный формат имени пользователя!").build();
            return response;
        }
        try {
            String resp = authService.registerCheck(userName);
            return Response.ok().entity(resp).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Возникла ошибка, попробуйте еще раз").build();
        }
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    @Produces("text/plain")
    public Response login(@FormParam("user") String userName, @FormParam("password") String password) {
        if (checkNameLength(userName)) {
            Response response = Response.status(Response.Status.LENGTH_REQUIRED)
                    .entity("Неверный формат имени пользователя!").build();
            return response;
        }
        if (checkPasswordLength(password)) {
            return Response.status(Response.Status.LENGTH_REQUIRED).entity("Неверный формат пароля!").build();
        }
        String result;
        try {
            result= authService.login(userName, password);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Неудачная попытка входа").build();
        }
        if (result != null) {
            return Response.status(Response.Status.OK).entity(result).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Неудачная попытка входа").build();
    }


    // TODO: 4/14/17  запилить логаут
    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String valueToken) {

        authService.logout(valueToken.split(" ")[1]);
        return Response.status(Response.Status.OK).build();
    }

    private boolean checkNameLength(String userName) {
        if (userName == null) {
            return false;
        }
        return (userName.length() > MAX_USER_NAME_LEN || userName.length() < MIN_USER_NAME_LEN);
    }

    private boolean checkPasswordLength(String password) {
        if (password == null) {
            return false;
        }
        return (password.length() < MIN_PASSWORD_LEN || password.length() > MAX_PASSWORD_LEN);
    }



}
