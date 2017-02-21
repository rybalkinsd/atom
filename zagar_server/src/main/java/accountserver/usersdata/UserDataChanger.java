package accountserver.usersdata;

import accountserver.authInfo.User;
import accountserver.api.Authentification;
import accountserver.api.Autorized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

//10.3.13.96

/**
 * Created by User on 25.10.2016.
 */
@Path("/profile")
public class UserDataChanger {
    private static final Logger log = LogManager.getLogger(UserDataChanger.class);

    @Autorized
    @POST
    @Path("{parameter: name|password|email}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changeName(@FormParam("name")String newName, @FormParam("email")String newEmail,
                               @FormParam("password")String newPassword, @Context HttpHeaders headers){
        try {
            if (newName == null && newEmail == null && newPassword == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            String authorizationHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
            String token = authorizationHeader.substring("Bearer".length()).trim();
            User currentUser = Authentification.userDAO.getUserById(
                    Authentification.tokenDAO.getUserIdByStringToken(token));
            if(Authentification.userDAO.isNameInUse(newName)==true){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            currentUser = newName != null ? currentUser.setLogin(newName): currentUser ;
            currentUser = newEmail != null ? currentUser.setEmail(newEmail): currentUser ;
            currentUser = newPassword != null ? currentUser.setPassword(newPassword): currentUser ;

            Authentification.userDAO.update(currentUser);
            log.info("User '{}' has new data", currentUser);
            return Response.ok("Data changed").build();
        } catch (Exception e){
            log.info("Error data changing");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
