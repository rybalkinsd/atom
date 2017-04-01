package ru.atom.rk1.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Path("/")
public class AuthResource implements Resource {
    private static final Logger log = LogManager.getLogger(AuthResource.class);

    static final ConcurrentHashMap<String, User> registered = new ConcurrentHashMap<>();

    private static String resp500 = "Sorry, something went wrong ...";
    private static String resp403 = "Bad name and/or password";

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/register")
    public Response register(@FormParam("name") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (name.length() < 3) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too short name, sorry :(").build();
        }

        if (name.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too long name, sorry :(").build();
        }

        if (!Pattern.compile("^[a-z]\\w{2,29}", Pattern.CASE_INSENSITIVE)
                .matcher(name).matches()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Login must stats with a letter and then contains " +
                            "only letters, numbers or '_'.")
                    .build();
        }

        if (Pattern.compile("[hg]itler", Pattern.CASE_INSENSITIVE)
                .matcher(name).find()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Gitler not allowed, sorry :(").build();
        }

        if (registered.containsKey(name)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Already registered").build();
        }

        if (password.length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too short password").build();
        }

        if (password.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Too long password").build();
        }

        if (!Pattern.compile("^[\\w]{8,20}$", Pattern.CASE_INSENSITIVE)
                .matcher(password).matches()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Password must be only from letters and / or numbers")
                    .build();
        }

        User user = new User(name, password);
        log.info("[" + name + "] registered");
        registered.put(name, user);
        return Response.ok("You've successfully registered").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/login")
    public Response login(@FormParam("name") String name,
                          @FormParam("password") String password) {
        if (name == null || password == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (!registered.containsKey(name)) {
            log.info(String.format("[%s] no such registered user\n", name));
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(resp403).build();
        }

        User user = registered.get(name);

        if (!user.checkPassword(password)) {
            log.info(String.format("[%s] tried to login with bad password\n", user.getName()));
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(resp403).build();
        }

        log.info("[" + name + "] logged in");

        if (!TokenStorage.put(user)) {
            log.warn("token collision");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(resp500).build();
        }

        return Response.ok(user.getToken().string()).build();
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/logout")
    public Response logout(@HeaderParam("Authorization") String token) {
        User user = TokenStorage.getUser(token);
        TokenStorage.remove(user);
        log.info("[" + user.getName() + "] logged out");
        return Response.ok("You've successfully logged out").build();
    }

    @GET
    @Produces("application/json")
    @Path("data/users")
    public Response users() {
        String usersJson = TokenStorage.getAllLoginedUsers().stream().map(User::toJson)
                .collect(Collectors.joining(", ", "{\"users\" : [", "]}"));
        return Response.ok(usersJson).build();
    }

}
