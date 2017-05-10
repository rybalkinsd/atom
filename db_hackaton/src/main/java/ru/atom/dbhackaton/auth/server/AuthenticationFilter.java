package ru.atom.dbhackaton.auth.server;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.auth.dao.Database;
import ru.atom.dbhackaton.auth.dao.UserDao;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Authorized
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
    private static String token;

    public AuthenticationFilter() {
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Check your request, please");
        }

        token = authorizationHeader.substring("Bearer".length()).trim();

        if (!validateToken(token)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean validateToken(String token) throws RuntimeException {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByToken(session, token) != null) {
                return true;
            }
            txn.commit();

            return false;
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return false;
        }
    }
}