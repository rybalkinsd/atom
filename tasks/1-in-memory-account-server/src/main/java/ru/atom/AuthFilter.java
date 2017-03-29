package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by alex on 28.03.17.
 */
@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null)
            throw new NotAuthorizedException("Not authorization header");
        Long token = Long.parseLong(authorizationHeader.trim());
        try {
            validateToken(token);
        } catch (NotAuthorizedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(Long token) {
        User user = StorageOfUsers.getUserByToken(token);
        if (user == null) {
            logger.info("User with token {} is not authorized", token);
            throw new NotAuthorizedException("Token is incorrect");
        }
    }
}
