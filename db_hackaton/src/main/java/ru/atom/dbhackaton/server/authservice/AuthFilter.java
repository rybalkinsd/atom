package ru.atom.dbhackaton.server.authservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.TokenDao;
import ru.atom.dbhackaton.server.model.Token;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


/**
 * Created by pavel on 14.04.17.
 */

@Authorized
@Provider
public class AuthFilter implements ContainerRequestFilter {
    private static Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            throw new NotAuthorizedException("Not authorization header");
        }

        long token = Long.parseLong(authorizationHeader.split("Bearer ")[1]);

        try {
            validateTocken(token);
        } catch (NotAuthorizedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateTocken(long token) {
        Token tmp = TokenDao.getInstance().getToken(Database.session(), token);
        if (tmp == null) {
            logger.info("User with tocken {} is not authorized", token);
            throw new NotAuthorizedException("Tocken is incorrect");
        }
    }
}
