package ru.atom.dbhackaton.server.auth;

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
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        String token = extractTokenStringFromAuthHeader(authorizationHeader);

        if (token == null) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        try {
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public static String extractTokenStringFromAuthHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.substring("Bearer".length()).trim();
    }

    public static Long extractTokenFromAuthHeader(String header) {
        try {
            return Long.parseLong(extractTokenStringFromAuthHeader(header));
        } catch (Exception e) {
            return null;
        }
    }

    private void validateToken(String token) throws Exception {
        if (UserDao.getInstance().getByToken(Database.session(), Long.parseLong(token)) == null) {
            throw new Exception("Invalid token " + token);
        }
    }
}
