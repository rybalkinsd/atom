package ru.atom.dbhackaton.mm;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.model.TokenStorage;
import ru.atom.dbhackaton.model.UserStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ilysk on 16.04.17.
 */
@Path("/")
public class MatchMaker {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);

    @POST
    @Path("/join")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public static Response join(@FormParam("name") String name,
                         @FormParam ("token") String token) {

        Long longToken = Long.parseLong(token);
        if (TokenStorage.getByToken(longToken) != null) {
            return Response.ok("wtfis.ru:8090/gs/12345").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/finish")
    @Consumes("application/x-www-form-urlencoded")
    public static Response finish(@FormParam("gameResult") String gameResult) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> gameResultMapHead = new HashMap<String, Object>();

            // convert JSON string to Map
            gameResultMapHead = mapper.readValue(gameResult, new TypeReference<Map<String, String>>(){});

            long gameID = (long) gameResultMapHead.get("id");
            String resultsString = (String) gameResultMapHead.get("results");

            Map<String, Object> gameResultMapBody = new HashMap<String, Object>();

            // convert JSON string to Map
            gameResultMapBody = mapper.readValue(resultsString, new TypeReference<Map<String, String>>(){});

            Iterator it = gameResultMapBody.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                RegistredEntity user = UserStorage.getByName((String) pair.getKey());
                UserGameResult userGameResult = new UserGameResult(gameID, user, (int) pair.getValue());
                UserGameResultDao.saveGameResults(userGameResult);
            }

        } catch (JsonGenerationException e) {
            Response.status(Response.Status.BAD_REQUEST).build();
        } catch (JsonMappingException e) {
            Response.status(Response.Status.BAD_REQUEST).build();
        } catch (IOException e) {
            Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }
}
