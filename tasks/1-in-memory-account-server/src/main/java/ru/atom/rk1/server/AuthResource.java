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
public class AuthResource {
    private static final Logger log = LogManager.getLogger(AuthResource.class);

    private static final ConcurrentHashMap<String, User> registed = new ConcurrentHashMap<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/register")
    public Response register(@FormParam("name") String name,
                             @FormParam("password") String password) {
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

        if (registed.containsKey(name)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Already registed").build();
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
        log.info("[" + name + "] registed");
        registed.put(name, user);
        return Response.ok("You've successfully registed").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/login")
    public Response login(@FormParam("name") String name,
                          @FormParam("password") String password) {
        String badResp = "Bad name and/or password";

        if (!registed.containsKey(name)) {
            log.info(String.format("[%s] tried to login\n", name));
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(badResp).build();
        }

        User user = registed.get(name);

        if (!user.checkPassword(password)) {
            log.info(String.format("[%s] tried to login with bad password\n", user.getName()));
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(badResp).build();
        }

        log.info("[" + name + "] logged in");

        TokenStorage.put(user);

        return Response.ok(user.getToken().string()).build();
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/logout")
    public Response logout(@HeaderParam("Authorization") String token) {
        User user = TokenStorage.getUser(token);
        if (user == null) {
            log.warn("null user");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong ...").build();
        }

        if (TokenStorage.remove(user) == null) {
            log.warn("could not remove user");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong ...").build();
        }

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
