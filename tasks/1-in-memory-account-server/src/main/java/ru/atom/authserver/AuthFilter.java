package ru.atom.authserver;

import ru.atom.entities.Token;
import ru.atom.entities.TokenContainer;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by ikozin on 28.03.17.
 */
@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null) {
            throw new NotAuthorizedException("Token not provided");
        }

        String token = authHeader.trim().substring(7);
        try {
            validate(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token").build());
        }
    }

    private void validate(String tokenString) throws Exception {
        Long token = Long.valueOf(tokenString);
        if(!TokenContainer.validate(token)) {
            throw new Exception();
        }
    }
}
