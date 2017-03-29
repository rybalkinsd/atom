package ru.atom.resources;

import org.apache.logging.log4j.LogManager;
import ru.atom.base.User;
import ru.atom.storages.TokenStorage;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.IllegalFormatException;

@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!authorizationHeader.contains("Bearer ")) {
            Response.status(Response.Status.BAD_REQUEST).entity("Неверный формат данных").build();
        }

        if (authorizationHeader == null) {
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

    private void checkToken(String valueToken) {
        User user = TokenStorage.getUser(valueToken);
        if (user == null) {
            logger.info("Token = {} не актуален", valueToken);
            throw new NotAuthorizedException("Token = {} не актуален", valueToken);
        }
    }
}
