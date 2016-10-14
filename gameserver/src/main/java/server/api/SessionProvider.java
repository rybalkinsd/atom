package server.api;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import server.auth.Authentication;
import server.auth.Authorized;
import server.entities.User;
import server.session.GameSessionBatchHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.CopyOnWriteArrayList;

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
        CopyOnWriteArrayList<User> users = Authentication.getServerUsers();
        if (users.isEmpty()) {
            return Response.ok("{\"sessions\": []}").build();
        }
        Player player = new Player(users.get(0));
        singlePlayerMatchMaker.joinGame(player);

        return Response.ok(new GameSessionBatchHolder(
                singlePlayerMatchMaker.getActiveGameSessions()).writeJson()).build();
    }

}
