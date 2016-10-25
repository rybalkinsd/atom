package server.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.auth.Authorized;
import server.model.token.Token;
import server.model.token.TokensContainer;
import server.model.user.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
    public Response setPlayerName(@HeaderParam("Authorization") final String rawToken,
                                  @FormParam("name")final String name) {

        try {

            if (name == null || name.equals("")) {
                if (log.isWarnEnabled()) {
                    log.warn("Empty or null name are not required");
                }
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Token token = TokensContainer.parseToken(rawToken);

            if (!TokensContainer.containsToken(token)) {
                if (log.isWarnEnabled()) {
                    log.warn("Wrong token - {}", token);
                }
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            String maybeNullName = Authentication.getRegisterUsers().stream()
                    .map(User::getName)
                    .filter(name::equals)
                    .findFirst()
                    .orElse(null);

            if (maybeNullName != null) {
                if (log.isWarnEnabled()) {
                    log.warn("User with name {} already registered", name);
                }
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = TokensContainer.getUser(token);
            String oldName = user.getName();
            TokensContainer.removeToken(token);
            user.setName(name);
            TokensContainer.addToken(user, token);
            if (log.isInfoEnabled()) {
                log.info("User with old name {} set name to {}", oldName, name);
            }
            return Response.ok("Your name successfully changed to " + name).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
