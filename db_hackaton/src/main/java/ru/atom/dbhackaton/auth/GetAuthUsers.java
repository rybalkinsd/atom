package ru.atom.dbhackaton.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

@Path("/data")
public class GetAuthUsers {
    @NotNull
    private static final Logger log = LogManager.getLogger(GetAuthUsers.class);

    //curl -i
    //     -X POST
    //     -H "Authorization: Bearer {token}"
    //     -H "Content-Type: application/x-www-form-urlencoded"
    //     -H "Host: localhost:8080"
    //     -d "gender=FEMALE"
    // "localhost:8080/data/personsbatch"

    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("users")
    public Response getPersonsBatch(ContainerRequestContext requestContext) throws Exception {
        log.info("Batch of authorised users requested.");
        return Response.ok("").build();
    }

}