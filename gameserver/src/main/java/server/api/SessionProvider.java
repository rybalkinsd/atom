package server.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.auth.Authentication;
import session.SessionBatchHolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("activeGameSessions")
public class SessionProvider {

    private static final Logger LOG = LogManager.getLogger(SessionProvider.class);

    @GET
    @Produces("application/json")
    public final Response getSessionsBatch() throws JsonProcessingException {
        LOG.info("Batch of sessions requested.");
        return Response.ok(new SessionBatchHolder(
                Authentication.getMatchMaker().
                        getActiveGameSessions()).writeJson()).build();
    }

}
