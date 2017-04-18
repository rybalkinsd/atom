package ru.atom.dbhackaton.server;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.atom.dbhackaton.server.Dao.Database;
import ru.atom.dbhackaton.server.Dao.TokenDao;
import ru.atom.dbhackaton.server.Dao.UserDao;
import ru.atom.dbhackaton.server.model.GameResults;
import ru.atom.dbhackaton.server.model.GameSession;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;
import ru.atom.dbhackaton.server.service.GameSessionService;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavel on 17.04.17.
 */
@Path("/")
public class MMResources {

    private static final GameSessionService GAME_SESSION_SERVICE = new GameSessionService();

    @GET
    @Path("/join")
    @Produces("text/plain")
    public Response join(String body) {
        String userName = body.split("name=\\{")[1].split("}token")[0];
        String token = body.split("token=\\{")[1].split("}")[0];

        Session session = Database.session();
        Token loginedToken = TokenDao.getInstance().getTokenByUserName(session, userName);

        if (loginedToken == null || loginedToken.getToken() != Long.parseLong(token)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not logined").build();
        }

        User loginedUser = UserDao.getInstance().getByName(session, userName);
        ThreadSafeQueue.getInstance().add(loginedUser);

        String GameURL = "wtfis.ru:8090/gs/"
                + ThreadSafeStorage.getCurrentGameId();
        return Response.ok(GameURL).build();
    }

    @POST
    @Path("/finish")
    @Consumes("application/x-www-form-urlencoded")
    public Response finish(String jsonBody) {
        JSONObject json = new JSONObject(jsonBody);
        long gameId = json.getLong("id");
        JSONArray array = json.getJSONArray("result");
        List<GameResults> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            GameResults result = new GameResults().setGameId(gameId);
            int points = array.getJSONObject(i).getInt("points");
            String name = array.getJSONObject(i).getString("user");
            User user = new User();
            user.setName(name);
            list.add(new GameResults(gameId, user, points));

        }
        GAME_SESSION_SERVICE.addGameToDataBase(list);

        return Response.ok().build();
    }
}
