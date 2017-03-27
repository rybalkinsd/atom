package ru.atom.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.base.User;
import ru.atom.storages.AccountStorage;
import ru.atom.storages.TokenStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by mkai on 3/26/17.
 */
@Path("/auth")
public class AuthResource {
    private static final Logger logger = LogManager.getLogger(AuthResource.class);
    private static final int MIN_USER_NAME_LEN = 6;
    private static final int MAX_USER_NAME_LEN = 30;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int MAX_PASSWORD_LEN = 30;

    private static TokenStorage tokenStorage = new TokenStorage();
    private static AccountStorage accountStorage = new AccountStorage();
    //private static ConcurrentArrayQueue<User> onlineUsers = new ConcurrentArrayQueue();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    @Produces("text/plain")
    public Response register(@QueryParam("user") String userName, @QueryParam("password") String password) {
        if (userName.length() > MAX_USER_NAME_LEN || userName.length() < MIN_USER_NAME_LEN) {
            Response response = Response.status(Response.Status.BAD_REQUEST)
                    .entity("Неверный формат имени пользователя!").build();
            return response;
        }
        if (password.length() < MIN_PASSWORD_LEN || password.length() > MAX_PASSWORD_LEN) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Неверный формат пароля!").build();
        }
        if (accountStorage.isUserExist(userName)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Пользователь с таким именем уже зарегистрирован").build();
        }
        accountStorage.addAccount(userName, password);
        logger.info("[" + userName + "] успешно зарегистрирован");
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    @Produces("text/plain")
    public Response login(@QueryParam("user") String userName, @QueryParam("password") String password) {
        if (userName.length() > MAX_USER_NAME_LEN || userName.length() < MIN_USER_NAME_LEN) {
            Response response = Response.status(Response.Status.BAD_REQUEST)
                    .entity("Неверный формат имени пользователя!").build();
            return response;
        }
        if (password.length() < MIN_PASSWORD_LEN || password.length() > MAX_PASSWORD_LEN) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Неверный формат пароля!").build();
        }
        if (!accountStorage.isUserExist(userName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Неверный логин или пароль!").build();
        }
        User user = accountStorage.getUser(userName);
        if (user.checkPassword(password)) {
            if (tokenStorage.containsUser(user)) {
                Response response = Response.ok(user.getToken().getValueToken()).build();
                return response;
            } else {
                user.setToken(tokenStorage.generateToken());
                //user.setStatus(User.Status.ONLINE);
                tokenStorage.addToken(user.getToken(), user);
                //onlineUsers.add(user);
                logger.info("[" + userName + "] успешно залогинился");
                return Response.ok(user.getToken()).build();
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Неверный логин или пароль!").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout() {
        return Response.ok().build();
    }

}
