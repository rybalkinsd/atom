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

//    if (credentials.putIfAbsent(user, password) != null) {
//      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
//    }
    User newUser = new User(user)
            .setPassword(password);
    log.info("New temp user created {} ", user);
    //Checking acceptability of username
    if (!TokenContainer.addUser(newUser)) {
      log.info("Not Acceptable you ruined registration");
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
      User currentUser = TokenContainer.getUserByString(user);
      log.info(currentUser.getName() + "not bad request");
      if ((currentUser.getName() == null)||(!TokenContainer.authenticate(currentUser,user, password))) {
        log.info("Bad password");
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      // Issue a token for the user
      Token token = TokenContainer.issueToken(currentUser);
      log.info("User '{}' logged in, token '{}'", user, token.getToken());

      // Return the token on the response
      return Response.ok(Long.toString(token.getToken())).build();

    } catch (Exception e) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

}
