package accountserver.api.data;

import accountserver.database.leaderboard.LeaderboardDao;
import accountserver.database.users.User;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.SortedByValueMap;
import utils.json.JSONHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Klissan on 06.11.2016.
 * LeaderboardApi
 */

@Path("/data")
public class LeaderboardApi {

    @NotNull
    private static final Logger log = LogManager.getLogger(LeaderboardApi.class);

    /*Protocol: HTTP
    Path: data/leaderboard
    Method: GET
    Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)*/
    @GET
    @Produces("application/json")
    @Path("leaderboard")
    public Response getTopUsers(@QueryParam("N") int count) {
        log.info("Top '{}' users by scores requested", count);

        //get leaders
        Map<User, Integer> leaders = ApplicationContext
                .instance()
                .get(LeaderboardDao.class)
                .getTopUsers(count);

        //System.out.println(JSONHelper.toJSON(leaders,new TypeToken<Map<User,Integer>>(){}.getType()));

        LeaderboardApi.UserInfo ret = new LeaderboardApi.UserInfo();
        leaders.forEach((User user, Integer score) -> ret.leadersWithScore.put(
                user.getName(),
                score
        ));
        ret.leadersWithScore = SortedByValueMap.sortByValues(ret.leadersWithScore);
        //отправляем ответ
        return Response.ok(
                JSONHelper
                        .toJSON(ret,
                                new TypeToken<LeaderboardApi.UserInfo>() {
                                }
                                        .getType()
                        )
        ).build();
    }

    public static class UserInfo {
        @Expose
        public Map<String, Integer> leadersWithScore = new HashMap<>();
        //name + score
    }
}
