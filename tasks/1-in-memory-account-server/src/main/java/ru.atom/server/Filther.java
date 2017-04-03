package ru.atom.server;

import ru.atom.token.Token;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by salvador on 01.04.17.
 */
@Authorized
@Provider
public class Filther implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String tokenStr) throws Exception {
        if (!ServerResourses.loggedUsers.containsKey(new Token(tokenStr))) {
            throw new NotAuthorizedException("Invalid token");
        }

    }

}
