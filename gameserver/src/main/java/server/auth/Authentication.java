package server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import server.model.token.Token;
import server.model.token.TokensContainer;
import server.model.user.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/auth")
public class Authentication {

    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static CopyOnWriteArrayList<User> registerUsers;

    static {
        registerUsers = new CopyOnWriteArrayList<>();
    }

    @NotNull
    public static CopyOnWriteArrayList<User> getRegisterUsers() {
        return registerUsers;
    }

    // curl -i
    //      -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: {IP}:8080"
    //      -d "login={}&password={}"
    // "{IP}:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String name,
                             @FormParam("password") String password) {

        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = registerUsers.parallelStream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (user != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        user = new User(name, password);
        registerUsers.add(user);
        log.info("New user registered with login {}", name);
        return Response.ok(user + " registered.").build();

    }

    // curl -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Host: localhost:8080"
    //      -d "login=admin&password=admin"
    // "http://localhost:8080/auth/login"
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("login") String name,
                                     @FormParam("password") String password) {

        if (name == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {

            boolean isRegister = registerUsers.parallelStream()
                    .filter(user -> user.getName().equals(name))
                    .filter(user -> user.getPassword().equals(password))
                    .count() == 1;

            if (!isRegister) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            Token token = TokensContainer.issueToken(name);
            log.info("Player with name {} successfully logged in", name);
            return Response.ok(Long.toString(token.getToken())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // curl -X POST
    //      -H "Authorization: Bearer {token}"
    //      -H "Host: localhost:8080
    // "http://localhost:8080/auth/logout"
    @Authorized
    @POST
    @Path("logout")
    @Produces("text/plain")
    public Response logoutUser(@HeaderParam("Authorization") String rawToken) {

        try {

            Token token = TokensContainer.parseToken(rawToken);

            if (!TokensContainer.containsToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                User user = TokensContainer.getUser(token);
                TokensContainer.removeToken(token);
                registerUsers.remove(user);
                if (log.isInfoEnabled()) {
                    log.info("Player with name {} logout", user.getName());
                }
                return Response.ok("Player with name " + user.getName() + " logout").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
