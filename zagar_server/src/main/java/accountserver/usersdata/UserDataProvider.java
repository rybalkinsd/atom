package accountserver.usersdata;

import accountserver.api.Authentification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 25.10.2016.
 */
@Path("/data")
public class UserDataProvider {
    private static final Logger log = LogManager.getLogger(UserDataProvider.class);

    @GET
    @Path("/users")
    @Produces("application/json")
    public Response getLoggedInUsersList(){
        try{
            log.info("Users JSON requested");
            List<Token> tokens = Authentification.tokenDAO.getAll();
            ArrayList<User> loggedInUsers = new ArrayList<>();
            for(Token token: tokens){
                loggedInUsers.add(Authentification.userDAO.getUserById(token.getUserId()));
            }
            return Response.ok((new UsersJSON(loggedInUsers)).writeJson()).build();
        } catch (Exception e){
            log.info("Error sending users info");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("leaderboard")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response getLeaders1(@QueryParam("N") int N){
        List<Leader> n1;
        n1= Authentification.LB.getAll(N);
        String S="{ ";
        if(N>n1.size()) N=n1.size();
        for(int i=0;i<n1.size();i++) {
            S += n1.get(i).toJSON();
            if (i<n1.size()-1) S+=", ";
        }
        S+=" }";
        log.info("S");
        return Response.ok(S).build();
    }

}
