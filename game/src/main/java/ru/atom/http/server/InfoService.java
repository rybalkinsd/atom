package ru.atom.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by zarina on 23.03.17.
 */
@Path("/data/")
public class InfoService {
    private static final Logger log = LogManager.getLogger(InfoService.class);

    @GET
    @Produces("text/plain")
    @Path("/list")
    public Response list() {
        log.info("List request");
        return Response.ok()
                .build();
    }
}
