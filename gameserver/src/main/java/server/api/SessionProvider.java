package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authorized;
import session.SessionBatchHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("activeGameSessions")
public class SessionProvider {

    private static final Logger log = LogManager.getLogger(SessionProvider.class);

    //@Authorized
    @GET
    @Produces("application/json")
    public Response getSessionsBatch() throws JsonProcessingException {
        log.info("Batch of sessions requested.");

        //Added for test reasons.
        MatchMaker singlePlayerMatchMaker = new SinglePlayerMatchMaker();
        Player player = new Player("Arkady");
        singlePlayerMatchMaker.joinGame(player);

        return Response.ok(new SessionBatchHolder(
                singlePlayerMatchMaker.getActiveGameSessions()).writeJson()).build();
    }

}
