package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.persons.Token;
import ru.atom.persons.TokenStorage;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by BBPax on 28.03.17.
 */
@LogoutSecured
@Provider
public class LogoutBrowserFilter implements ContainerRequestFilter {
    private static final Logger log = LogManager.getLogger(LogoutBrowserFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getHeaders().get("Authorization") != null) {
            String token = requestContext.getHeaders().get("Authorization").get(0);
            try {
                tokenValidate(token);
            } catch (NotAuthorizedException e) {
                log.info("TokenStorage hasn't token with value: {}", token.substring(7));
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid Token.")
                        .build());
            }
        }
    }

    private void tokenValidate(String token) throws NotAuthorizedException {
        if (token == null) {
            throw new NullPointerException();
        }
        if (!TokenStorage.hasToken(new Token(token.substring(7)))) {
            throw new NotAuthorizedException("This token is not contained");
        }
    }
}
