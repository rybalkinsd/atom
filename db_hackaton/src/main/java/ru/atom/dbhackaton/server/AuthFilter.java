package ru.atom.dbhackaton.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;
import ru.atom.dbhackaton.service.AuthException;

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
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        Transaction txn = null;
        User user;
        Token tokenT;

        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            tokenT = TokenDao.getInstance().getToken(session, Long.valueOf(token));
            if (tokenT == null) {
                requestContext.abortWith(
                        Response.status(Response.Status.UNAUTHORIZED).entity("User is not logined").build());
            }
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
