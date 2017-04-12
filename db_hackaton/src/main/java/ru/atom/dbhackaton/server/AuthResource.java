package ru.atom.dbhackaton.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;


@Path("/")
public class AuthResource {
    private static final Logger log = LogManager.getLogger(AuthResource.class);

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/register")
    public Response register(@FormParam("user") String name, @FormParam("password") String password) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            log.info("Registering {} with password {}...", name, password);
            if (name == null || name.length() > 30 || name.contains("\"") || name.contains("\n")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid name.\n").build();
            }
            if (password == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Password is not specified!\n").build();
            }
            if (password == null || password.length() < 4) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Password is too short. Minimal allowed length of password: 4.\n").build();
            }
            if (UserDao.getInstance().getByName(session, name) != null) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Already registered.\n").build();
            }
            final String msg = "Registered new user " + name + ".\n";
            UserDao.getInstance().insert(session, new User(name, password));
            log.info(msg);

            txn.commit();

            return Response.ok(msg).build();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed!").build();
        }
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/login")
    public Response login(@FormParam("user") String name, @FormParam("password") String password) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            final User user = (name == null || password == null) ? null :
                    UserDao.getInstance().getByName(session, name);
            if (user == null) {
                if (password != null) log.info("User " + name + " does not exist!");
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Incorrect user name or password.\n").build();
            }

            if (!user.validatePassword(password)) {
                log.info("Incorrect password " + password + " for user " + name + "!");
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("Incorrect user name or password.\n").build();
            }

            if (user.isLogined()) log.info("User {} is already logined!", name);
            else log.info("User {} logined!", name);
            final Token token = TokenStore.getTokenForUser(user);

            txn.commit();

            return Response.ok(token.toString()).build();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed!").build();
        }
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            Long token = AuthFilter.extractTokenFromAuthHeader(authHeader);
            User user = TokenStore.getUserByToken(new Token(token));
            TokenStore.removeToken(user.getToken());
             user.resetToken();
             final String msg = "User " + user.name + " logged out!";
            log.info(msg);

            txn.commit();

            return Response.ok(msg).build();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed!").build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/data/users")
    public Response users() {
        String usersJson = TokenStore.getAllLoginedUsers().stream().map(User::toJson)
                .collect(Collectors.joining(", ", "{\"users\" : [", "]}"));
        return Response.ok(usersJson).build();
    }
}
