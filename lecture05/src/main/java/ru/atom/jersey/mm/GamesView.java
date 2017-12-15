package ru.atom.jersey.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.thread.mm.ThreadSafeStorage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by sergey on 3/15/17.
 */

@Path("/games")
public class GamesView {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    @GET
    @Produces("text/plain")
    public Response getView() {
        log.info("View request");
        return Response.ok(ThreadSafeStorage.getAll().toString())
                .build();
    }
}
