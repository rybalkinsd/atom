package ru.atom.auth.server;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import ru.atom.model.Token;
import ru.atom.model.TokenMap;

@Authorized
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    private static Token token;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Check your request, please");
        }

        token = new Token(Long.parseLong(authorizationHeader.substring("Bearer".length()).trim()));

        if (!validateToken(token)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public static Token getToken() {
        return token;
    }

    private boolean validateToken(Token token) {
        return TokenMap.validateToken(token);
    }
}