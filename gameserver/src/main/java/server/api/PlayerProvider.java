package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.player.Player;
import model.player.PlayerBatchHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Path("/data")
public class PlayerProvider {

    private static final Logger log = LogManager.getLogger(SessionProvider.class);

    @GET
    @Path("/users")
    @Produces("application/json")
    public Response getSessionsBatch() throws JsonProcessingException {
        log.info("Batch of persons requested.");
        ConcurrentHashMap<Player, Long> tokens = Authentication.getTokens();
        return Response.ok(new PlayerBatchHolder(new ArrayList<>(tokens.keySet())).writeJson()).build();
    }

}
