package server.profile;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/profile")
public class PlayerProfile {

    private static final Logger log = LogManager.getLogger(PlayerProfile.class);

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

            Long token = Long.parseLong(rawToken.substring("Bearer".length()).trim());

            if (!Authentication.getTokensReversed().containsKey(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                Player player = Authentication.getTokensReversed().get(token);
                player.setName(name);
                if (log.isInfoEnabled()) {
                    log.info("Player with login {} set name to {}", player.getLogin(), name);
                }
                return Response.ok("Your name successfully changed to " + name).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
