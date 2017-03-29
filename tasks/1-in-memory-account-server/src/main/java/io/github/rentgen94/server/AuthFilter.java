package io.github.rentgen94.server;

import io.github.rentgen94.Token;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static io.github.rentgen94.MyLogger.getLog;
import static io.github.rentgen94.WorkWithProperties.getStrBundle;
import static io.github.rentgen94.server.ServerResources.authUsers;

@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            getLog().error(getStrBundle().getString("wrong.header"));
            throw new NotAuthorizedException(getStrBundle().getString("auth.header.must.be"));
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.trim();

        try {
            // Validate the token
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(getStrBundle().getString("invalid.token")).build());
        }
    }

    private void validateToken(String token) throws Exception {
        token = token.substring("Bearer ".length());
        Token validateToken = new Token(token);
        if (!authUsers.containsKey(validateToken) || validateToken.getToken() == -1L) {
            // no exception in case of invalid token
            throw new NotAuthorizedException(getStrBundle().getString("invalid.token"));
        }
    }
}