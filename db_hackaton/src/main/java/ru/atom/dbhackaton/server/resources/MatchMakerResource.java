package ru.atom.dbhackaton.server.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.service.MatchMakerService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class MatchMakerResource {
    private static final Logger logger = LogManager.getLogger(MatchMakerResource.class);

    public static MatchMakerService matchMakerService = new MatchMakerService();


    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/join")
    @Produces("text/plain")
    public Response join(@FormParam("token") String token) {
        String url = "wtfis.ru:8090/gs/";
        logger.info(token);

        long gameSessionId = matchMakerService.join(token);
        if (gameSessionId == -1) {
            return Response.status(Response.Status.OK).entity("Please, wait :)").build();
        }

        return Response.status(Response.Status.OK).entity(url + gameSessionId).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/finish")
    @Produces("text/plain")
    public Response finish(String jsonString) {
        try {
            matchMakerService.finish(jsonString);
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect data " +
                    "Result saving is impossible").build();
        }
        return Response.status(Response.Status.OK).entity("Game finish succeed").build();
    }

}
