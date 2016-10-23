package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import matchmaker.MatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import gamemodel.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import server.model.user.User;
import session.SessionBatchHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("activeGameSessions")
public class SessionProvider {

    private static final Logger log = LogManager.getLogger(SessionProvider.class);

    @GET
    @Produces("application/json")
    public Response getSessionsBatch() throws JsonProcessingException {
        log.info("Batch of sessions requested.");

        //Added for test reasons.
        MatchMaker singlePlayerMatchMaker = new SinglePlayerMatchMaker();
        CopyOnWriteArrayList<User> serverUsers = Authentication.getRegisterUsers();
        if (serverUsers.isEmpty()) return Response.ok("{\"sessions\": []}").build();
        User user = serverUsers.get(0);
        Player player = new Player(user);
        singlePlayerMatchMaker.joinGame(player);

        return Response.ok(new SessionBatchHolder(
                singlePlayerMatchMaker.getActiveGameSessions()).writeJson()).build();
    }

}
