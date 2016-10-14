package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.model.token.Token;
import server.model.token.TokensContainer;
import server.model.user.User;
import server.model.user.UserBatchHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Path("/data")
public class UserProvider {

    private static final Logger log = LogManager.getLogger(UserProvider.class);

    @GET
    @Path("/users")
    @Produces("application/json")
    public Response getSessionsBatch() throws JsonProcessingException {
        log.info("Batch of users requested.");
        ConcurrentHashMap<User, Token> tokens = TokensContainer.getTokensByUsersMap();
        return Response.ok(new UserBatchHolder(new ArrayList<>(tokens.keySet())).writeJson()).build();
    }

}
