package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentArrayQueue;

import com.google.gson.Gson;

//import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
//import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
//import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
//import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Path("/")
public class AuthService {
    private static final Logger log = LogManager.getLogger(AuthService.class);

    private static final TokenList tokenList = new TokenList();
    private static final ConcurrentHashMap<String, User> registeredUsers = new ConcurrentHashMap<>();
    private static final ConcurrentArrayQueue<String> chat = new ConcurrentArrayQueue<>();

    public static User getUser(String name) {
        return registeredUsers.get(name);
    }

    public static boolean authorized(String token) {
        return token != null && tokenList.isValid(token);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/register")
    public Response register(@FormParam("user") String user,
        @FormParam("password") String password) {
        if (user.equals("") /* == null */ || password.equals("") /* == null */) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too short name, sorry :(")
                .build();
        }
        if (user.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(")
                .build();
        }
        if (registeredUsers.containsKey(user)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("This nickname has already been taken").build();
        }
        log.info("[" + user + "] registered");
        registeredUsers.put(user, new User(user, password));
        // NewCookie cookie = login0(user, password);
        return Response.ok("Registration successful.").build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/auth/login")
    public Response login(@FormParam("user") String user, @FormParam("password") String password) {
        if (user.equals("") || password.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("You have to enter you nickname and password :(").build();
        }
        if (user.length() > 30) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(")
                .build();
        }
        if (!registeredUsers.containsKey(user)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You have to register first")
                .build();
        }
        if (registeredUsers.containsKey(user)
            && !password.equals(registeredUsers.get(user).password)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect password")
                .build();
        }
        System.out.println(tokenList.getAllNames());
        /*
         * if (tokenList.isValid(user)) { return
         * Response.status(Response.Status.BAD_REQUEST).
         * entity("Already logged in").build(); }
         */
        Token token = new Token(user);
        tokenList.addToken(token);
        // NewCookie cookie = new NewCookie("token", token.getTokenName(), "/",
        // "", "SessionToken", 1000, false);
        log.info("[" + user + "] logged in");
        chat.add("[" + user + "] joined");
        return Response.ok(token.getTokenName()).build();
    }

    /*
     * private NewCookie login0(String name, String pass) {
     * 
     * Token token = new Token(name); tokenList.addToken(token); log.info("[" +
     * name + "] logged in"); chat.add("[" + name + "] joined"); return new
     * NewCookie("token", token.getTokenName(), "/", "", "SessionToken", 1000,
     * false); }
     */

    // Добавить JSON
    @GET
    @Produces("application/json")
    // @Produces("text/plain")
    @Path("/data/users")
    public Response online() {
        // String.join("\n", tokenList.getAllNames())
        Gson gson = new Gson();
        return Response.ok("{\"users\" : " + gson.toJson(tokenList.getAllNames()) + "}").build();
    }

    // Authorization доделать
    @POST
    @Authorized
    @Produces("text/plain")
    @Path("/auth/logout")
    public Response logout(@Context HttpHeaders headers) {
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        User user = tokenList.getUserByToken(token);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not logged in").build();
        }
        log.info("[" + user.getName() + "] logged out");
        chat.add("[" + user.getName() + "] left");
        tokenList.deleteToken(token);
        return Response.ok("Logged out.").build();
    }

    /*
     * @Override public void filter(ContainerRequestContext requestContext)
     * throws IOException { // Get the HTTP Authorization header from the
     * request String authorizationHeader =
     * requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
     * 
     * // Check if the HTTP Authorization header is present and formatted
     * correctly if (authorizationHeader == null) { throw new
     * NotAuthorizedException("Authorization header must be provided"); }
     * 
     * // Extract the token from the HTTP Authorization header String token =
     * authorizationHeader.trim();
     * 
     * 
     * try { // Validate the token authorized(token); } catch (Exception e) {
     * requestContext.abortWith(
     * Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized").
     * build()); } }
     */
}