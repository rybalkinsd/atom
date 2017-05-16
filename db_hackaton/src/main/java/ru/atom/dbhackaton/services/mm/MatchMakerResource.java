package ru.atom.dbhackaton.services.mm;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.resource.User;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by BBPax on 18.04.17.
 */
@Path("/")
public class MatchMakerResource {
    private static final Logger log = LogManager.getLogger(MatchMakerResource.class);
    private static final MatchMakerService mms = new MatchMakerService();

    @POST
    @Path("/join")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("text/plain")
    public Response join(@FormParam("token") String tokenS) {
        if (tokenS == null) {
            return  Response.status(Response.Status.BAD_REQUEST).entity("token is empty").build();
        }
        Long token = Long.valueOf(tokenS);
        User user = mms.findUser(token);
        if (!user.isValid()) {
            return  Response.status(Response.Status.BAD_REQUEST).entity("invalid user").build();
        }
        log.info("joining user: " + user.getLogin() + " with token: " + token);
        return  Response.ok().entity("http://localhost:8080/bomberman/frontend/").build();
    }

    /**
     * body: {"id":12345, "result":{"user1":10, "user2":15}}
     * @param result String of game's result, which will be written in DB
     * @return response ok() if everything is ok, and code = 400, if result is null
     */
    @POST
    @Path("/finish")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response finish(String result) {
        if (result == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("empty result").build();
        }
        JsonObject gson = new JsonParser().parse(result).getAsJsonObject();
        Integer gameId = gson.get("id").getAsInt();
        log.info("gameId = " + gameId);
        for (Map.Entry<String, JsonElement> entry : gson.get("result").getAsJsonObject().entrySet()) {
            User temp = mms.findUser(entry.getKey());
            mms.saveResult(gameId, temp, entry.getValue().getAsInt());
        }
        log.info(result);
        return Response.ok().build();
    }
}
