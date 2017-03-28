package ru.atom.rk01;

import ru.atom.rk01.resource.AuthServerResource;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by dmbragin on 3/28/17.
 */
@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Override

    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.trim();

        try {
            // Validate the token
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String tokenStr) throws Exception {
        // no exception in case of valid token
        Token token = new AuthToken();
        token.setTokenString(tokenStr);
        User user = AuthServerResource.getUserManager().getUserByToken(token);
        if (user == null) {
            throw new NotAuthorizedException("Invalid token");
        }
    }


}