package ru.atom.dbhackaton.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger log = LogManager.getLogger(AuthFilter.class);
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new NotAuthorizedException("Authorization header must be provided");
            }

            String token = authorizationHeader.substring("Bearer".length()).trim();

            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).entity("User is not logined").build());
        }
    }

    private void validateToken(String token) throws Exception {
        if (!AuthResource.validToken(token)) {
            log.info("Invalid token: " + token);
            throw new NotAuthorizedException("Invalid token");
        }
    }
}
