package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by Vlad on 28.03.2017.
 */
@Path("/data")
public class InfoServerResources {
    private static final Logger log = LogManager.getLogger(AuthServerResources.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response info() {
        log.info("Users login " + TokensStorage.toJson());
        return Response.ok(TokensStorage.toJson()).build();
    }
}
