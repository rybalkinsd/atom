package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.entities.token.TokensStorage;
import server.entities.user.UserBatchHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/data")
public class UserProvider {

    private static final Logger log = LogManager.getLogger(UserProvider.class);

    @GET
    @Path("/users")
    @Produces("application/json")
    public Response getUsersBatch() throws JsonProcessingException {
        log.info("Batch of users requested.");
        return Response.ok(new UserBatchHolder(TokensStorage.getUserList()).writeJson()).build();
    }

}
