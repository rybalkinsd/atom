package accountserver.api;

import info.AuthDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLClientInfoException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class AuthenticationServlet {
  @NotNull
  private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);
  @NotNull
  private static ConcurrentHashMap<String, Long> tokens;
  @NotNull
  private static ConcurrentHashMap<Long, String> tokensReversed;

  static {
    tokens = new ConcurrentHashMap<>();
    tokensReversed = new ConcurrentHashMap<>();
  }

  @POST
  @Path("register")
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/plain")
  public Response register(@FormParam("user") String user,
                           @FormParam("password") String password) {
    if (user == null || password == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    try {
      AuthDataStorage.registerNewUser(user, password);
      log.info("New user '{}' registered", user);
      return Response.ok("User " + user + " registered.").build();
    }
    catch(SQLClientInfoException e){
      log.info("User with name '{}' already exists.", user);
      return Response.status(Response.Status.CONFLICT).build();
    }
    catch(Exception e){
      log.info(e.getMessage());
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
  }

  @NotNull
  @POST
  @Path("login")
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/plain")
  public Response login(@NotNull @FormParam("user") String user,
                        @NotNull @FormParam("password") String password) {
    try {
      if (!AuthDataStorage.authenticate(user, password)) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      long token = issueToken(user);
      log.info("User '{}' logged in", user);
      return Response.ok(Long.toString(token)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

  public static void logout(String name){
    try{
      long token = tokens.get(name);
      tokens.remove(name);
      tokensReversed.remove(token);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @NotNull
  private Long issueToken(@NotNull String user) {
    Long token = tokens.get(user);
    if (token != null) {
      return token;
    }
    token = ThreadLocalRandom.current().nextLong();
    tokens.put(user, token);
    tokensReversed.put(token, user);
    return token;
  }

  public static boolean validateToken(@NotNull String rawToken) {
    Long token = Long.parseLong(rawToken);
    if (!tokensReversed.containsKey(token)) {
      return false;
    }
    log.info("Correct token from '{}'", tokensReversed.get(token));
    return true;
  }
}