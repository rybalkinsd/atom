package accountserver.api;

/**
 * Created by s.rybalkin on 28.09.2016.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Path("/auth")
public class AuthenticationServlet {
  @NotNull
  private static final Logger log = LogManager.getLogger(AuthenticationServlet.class);

  @NotNull
  private static ConcurrentHashMap<String, String> credentials;
  @NotNull
  private static ConcurrentHashMap<String, Long> tokens;
  @NotNull
  private static ConcurrentHashMap<Long, String> tokensReversed;

  static {
    credentials = new ConcurrentHashMap<>();
    credentials.put("admin", "admin");
    tokens = new ConcurrentHashMap<>();
    tokens.put("admin", 1L);
    tokensReversed = new ConcurrentHashMap<>();
    tokensReversed.put(1L, "admin");
  }

  // curl -i
  //      -X POST
  //      -H "Content-Type: application/x-www-form-urlencoded"
  //      -H "Host: {IP}:8080"
  //      -d "login={}&password={}"
  // "{IP}:8080/api/register"
  @NotNull
  @POST
  @Path("register")
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/plain")
  public Response register(@NotNull @FormParam("user") String user,
                           @NotNull @FormParam("password") String password) {

    if (user == null || password == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    if (credentials.putIfAbsent(user, password) != null) {
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    log.info("New user '{}' registered", user);
    return Response.ok("User " + user + " registered.").build();
  }


  // curl -X POST
  //      -H "Content-Type: application/x-www-form-urlencoded"
  //      -H "Host: localhost:8080"
  //      -d "login=admin&password=admin"
  // "http://localhost:8080/auth/login"
  @NotNull
  @POST
  @Path("login")
  @Consumes("application/x-www-form-urlencoded")
  @Produces("text/plain")
  public Response login(@NotNull @FormParam("user") String user,
                        @NotNull @FormParam("password") String password) {

    if (user == null || password == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    try {
      // Authenticate the user using the credentials provided
      if (!authenticate(user, password)) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      // Issue a token for the user
      long token = issueToken(user);
      log.info("User '{}' logged in", user);

      // Return the token on the response
      return Response.ok(Long.toString(token)).build();

    } catch (Exception e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

  private boolean authenticate(@NotNull String user, @NotNull String password) throws Exception {
    return password.equals(credentials.get(user));
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
