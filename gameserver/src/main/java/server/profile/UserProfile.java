package server.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;
import server.model.Token;
import server.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

@Path("/profile")
public class UserProfile {

    private static final Logger log = LogManager.getLogger(UserProfile.class);

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
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Token token = Authentication.parseToken(rawToken);
            ConcurrentHashMap<Token, User> tokensReversed = Authentication.getTokensReversed();

            if (!tokensReversed.containsKey(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                User user = tokensReversed.get(token);
                String oldName = user.getName();
                user.setName(name);
                if (log.isInfoEnabled()) {
                    log.info("User with old name {} set name to {}", oldName, name);
                }
                return Response.ok("Your name successfully changed to " + name).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
