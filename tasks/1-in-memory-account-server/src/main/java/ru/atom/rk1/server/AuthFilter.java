package ru.atom.rk1.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static final Logger log = LogManager.getLogger(AuthResource.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            // Extract the token from the HTTP Authorization header
            String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION).trim();

            validateToken(token);
        } catch (Exception e) {
            log.warn("unauthorized token " + authorizationHeader);
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("First you need to login")
                            .build());
        }
    }

    private void validateToken(String token) throws Exception {
        if (!TokenStorage.validate(token))
            throw new Exception();
    }
}
