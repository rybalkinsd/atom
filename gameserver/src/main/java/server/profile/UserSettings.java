package server.profile;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;
import server.entities.Token;
import server.entities.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("profile")
public class UserSettings {
    private static final Logger log = LogManager.getLogger(UserSettings.class);

    // curl -X POST
    //      -H "Content-Type: application/x-www-form-urlencoded"
    //      -H "Authorization: Bearer {token}"
    //      -H "Host: localhost:8080
    //      -d "name={newName}"
    // "http://localhost:8080/profile/name"
    @Authorized
    @POST
    @Path("/name")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response setPlayerName(@HeaderParam("Authorization") String rawToken,
                                  @FormParam("name") String name) {
        try {

            if (name == null || name.equals("")) {
                if (log.isWarnEnabled()) {
                    log.warn("Wrong name - " + name);
                }
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Long longToken = Long.parseLong(rawToken.substring("Bearer".length()).trim());
            Token token = new Token(longToken);

            if (!Authentication.getTokensReversed().containsKey(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                User user = Authentication.getTokensReversed().get(token);
                user.setName(name);
                if (log.isInfoEnabled()) {
                    log.info("Player with login {} set name to {}", user.getName(), name);
                }
                return Response.ok("Your name successfully changed to " + name).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
