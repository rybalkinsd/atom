package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.entities.token.Token;
import server.entities.token.TokensStorage;
import server.entities.user.User;
import server.entities.user.UserBatchHolder;

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
        return Response.ok(new UserBatchHolder(TokensStorage.getUserList()).writeJson()).build();
    }

}
