package ru.atom.dbhackaton.server.resources;

import org.apache.logging.log4j.LogManager;
import ru.atom.dbhackaton.server.base.User;
import ru.atom.dbhackaton.server.service.AuthException;
import ru.atom.dbhackaton.server.service.AuthService;
import ru.atom.dbhackaton.server.storages.TokenStorage;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AuthFilter.class);
    private static AuthService authService = new AuthService();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!authorizationHeader.contains("Bearer ")) {
            Response.status(Response.Status.BAD_REQUEST).entity("Неверный формат данных").build();
        }

        try {
            String valueToken = authorizationHeader.split(" ")[1];
            checkToken(valueToken);
        } catch (NotAuthorizedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Пользователь с таким токеном не найден").build());
        }
    }

    //TODO переделать
    private void checkToken(String valueToken) {
        if (authService.isAuth(valueToken)) {
            String msg = String.format("Token = {} is invalid", valueToken);
            logger.info(msg);
            throw new NotAuthorizedException(msg);
        }
    }
}
