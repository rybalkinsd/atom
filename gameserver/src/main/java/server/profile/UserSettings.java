package server.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.api.AuthenticationProvider;
import server.auth.Authorized;
import server.entities.token.Token;
import server.entities.token.TokensStorage;
import server.entities.user.User;

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
                log.warn("Wrong name - " + name);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Token token = TokensStorage.parse(rawToken);

            if (!TokensStorage.contains(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Boolean checkName = AuthenticationProvider.getRegisteredUsers().stream()
                    .map(User::getName)
                    .filter(name::equals)
                    .findFirst()
                    .isPresent();

            if (checkName) {
                log.warn("User try to change name, but name is already present: " + name);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = TokensStorage.getUser(token);
            String oldName = user.getName();
            TokensStorage.remove(token);
            user.setName(name);
            TokensStorage.add(user, token);
            log.info("User with name {} set name to {}", oldName, name);
            return Response.ok("Your name successfully changed to " + name).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
