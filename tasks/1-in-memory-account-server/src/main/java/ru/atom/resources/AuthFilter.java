package ru.atom.resources;

import org.apache.logging.log4j.LogManager;
import ru.atom.base.Token;
import ru.atom.base.User;
import ru.atom.storages.TokenStorage;

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

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            throw new NotAuthorizedException("");
        }

        try {
            checkToken(authorizationHeader);
        } catch (NotAuthorizedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void checkToken(String valueToken) {
        User user = TokenStorage.getUser(valueToken);
        if (user == null) {
            logger.info("привет");
            throw new NotAuthorizedException("");
        }
    }
}
