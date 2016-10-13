package server.auth;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class Authentication {

    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ConcurrentHashMap<String, String> registerData;
    private static ConcurrentHashMap<Player, Long> tokens;
    private static ConcurrentHashMap<Long, Player> tokensReversed;
    private static CopyOnWriteArrayList<Player> serverPlayers;

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
    public Response register(@FormParam("login") String login,
                             @FormParam("password") String password) {

        if (login == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (registerData.putIfAbsent(login, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        Player player = new Player(login, password);
        serverPlayers.add(player);
        log.info("New player registered with login {} and password {}", login, password);
        return Response.ok("Player " + player + " registered.").build();
    }

    static {
        registerData = new ConcurrentHashMap<>();
        tokens = new ConcurrentHashMap<>();
        tokensReversed = new ConcurrentHashMap<>();
        serverPlayers = new CopyOnWriteArrayList<>();
        registerData.put("admin", "admin");
//        tokens.put("admin", 1L);
//        tokensReversed.put(1L, "admin");
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
    public Response authenticatePlayer(@FormParam("login") String login,
                                       @FormParam("password") String password) {

        if (login == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            if (!authenticate(login, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            long token = issueToken(login);
            log.info("Player with login {} and password {} successfully logged in", login, password);
            return Response.ok(Long.toString(token)).build();

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

            Long token = Long.parseLong(rawToken.substring("Bearer".length()).trim());

            if (!tokensReversed.containsKey(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                Player player = tokensReversed.get(token);
                tokens.remove(player);
                tokensReversed.remove(token);
                if (log.isInfoEnabled()) {
                    log.info("Player with login {} logout", player.getLogin());
                }
                return Response.ok("Player with login " + player.getLogin() + " logout").build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    private boolean authenticate(String login, String password) throws Exception {
        return password.equals(registerData.get(login));
    }

    private Long issueToken(String login) {

        Player player = serverPlayers.stream()
                .filter(p -> p.getLogin().equals(login))
                .findFirst().orElse(null);

        Long token = tokens.get(player);
        if (token != null) {
            return token;
        }

        token = ThreadLocalRandom.current().nextLong();
        log.info("Generate new token {} for user with login {}", token, login);
        tokens.put(player, token);
        tokensReversed.put(token, player);
        return token;

    }

    static void validateToken(String rawToken) throws Exception {
        Long token = Long.parseLong(rawToken);
        if (!tokensReversed.containsKey(token)) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token from '{}'", tokensReversed.get(token));
    }

    public static ConcurrentHashMap<Player, Long> getTokens() {
        return tokens;
    }

    public static ConcurrentHashMap<Long, Player> getTokensReversed() {
        return tokensReversed;
    }

    public static CopyOnWriteArrayList<Player> getServerPlayers() {
        return serverPlayers;
    }
}
