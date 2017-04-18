package ru.atom.dbhackaton.mm;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.model.Token;
import ru.atom.dbhackaton.model.TokenStorage;
import ru.atom.dbhackaton.model.UserStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
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
        LoginEntity user = TokenStorage.getByToken(longToken);
        if (user != null) {
            log.info("user " + user.toString() + " join game");
            return Response.ok("wtfis.ru:8090/gs/12345").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/finish")
    @Consumes("application/x-www-form-urlencoded")
    public static Response finish(@FormParam("gameresult") String gameResult) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = new HashMap<String, Object>();

            // convert JSON string to Map
            map = mapper.readValue(gameResult, new TypeReference<Map<String, Object>>(){});

            long gameID = new Long(map.get("id").toString());

            Map<String, Object> gameResultMapBody = new HashMap<String, Object>();
            gameResultMapBody = (Map<String, Object>) map.get("result");

            for (Object o : gameResultMapBody.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                String userString = (String) pair.getKey();
                if (TokenStorage.getLoginByName(userString) != null) {
                    RegistredEntity user = UserStorage.getByName(userString);
                    UserGameResult userGameResult = new UserGameResult(gameID, user.getUserId(), (int) pair.getValue());
                    UserGameResultDao.saveGameResults(userGameResult);
                    log.info("user " + userString + " finished game id#" + gameID
                            + " with score " + pair.getValue().toString());
                } else {
                    log.info("No logined user: " + userString);
                }
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
