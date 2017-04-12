package ru.atom.dbhackaton.server.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.base.Token;
import ru.atom.dbhackaton.server.base.User;
import ru.atom.dbhackaton.server.storages.AccountDao;
import ru.atom.dbhackaton.server.storages.TokenStorage;

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
    private static final int MIN_USER_NAME_LEN = 0;
    private static final int MAX_USER_NAME_LEN = 21;
    private static final int MIN_PASSWORD_LEN = 0;
    private static final int MAX_PASSWORD_LEN = 21;

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
        if (AccountDao.isUserExist(userName)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Пользователь с таким именем уже зарегистрирован").build();
        }
        AccountDao.addAccount(userName, password);
        logger.info("[" + userName + "] успешно зарегистрирован");
        return Response.ok().entity("[" + userName + "] успешно зарегистрирован").build();
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
        if (!AccountDao.isUserExist(userName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Неверный логин или пароль!").build();
        }
        User user = AccountDao.getUser(userName);
        if (user.checkPassword(password)) {
            if (TokenStorage.containsUser(user)) {
                Response response = Response.ok(user.getToken().getValueToken()).build();
                return response;
            } else {
                user.setToken(TokenStorage.generateToken());
                TokenStorage.addToken(user.getToken(), user);
                logger.info("[" + userName + "] успешно залогинился");
                Response response = Response.ok(user.getToken().getValueToken()).build();
                return response;
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Неверный логин или пароль!").build();
    }

    private boolean checkNameLength(String userName) {
        if (userName == null)
            return false;
        return (userName.length() > MAX_USER_NAME_LEN || userName.length() < MIN_USER_NAME_LEN);
    }

    private boolean checkPasswordLength(String password) {
        return (password.length() < MIN_PASSWORD_LEN || password.length() > MAX_PASSWORD_LEN);
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String valueToken) {
        Token token = TokenStorage.getToken(valueToken.split(" ")[1]);
        String userName = TokenStorage.getUser(token).getName();
        if (TokenStorage.removeToken(token)) {
            logger.info("Пользователь {} вышел из системы", userName);
            return Response.ok().entity("До свидания, " + userName).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Что-то пошло не так: неактуальный токен").build();
    }

}
