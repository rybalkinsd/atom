package ru.atom.dbhackaton.server.mm;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.auth.Database;
import ru.atom.dbhackaton.server.auth.User;
import ru.atom.dbhackaton.server.auth.UserDao;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Path("/")
public class MatchMakerResource {
    private static final Logger log = LogManager.getLogger(MatchMakerResource.class);

    private static String[] connections;

    static long counterStart() {
        final Integer max = (Integer) Database.session().createNativeQuery("select max(gameId) from bomber.result")
                .uniqueResult();
        return max == null ? 0 : (max + 1) * 4;
    }

    private static AtomicLong numConnections = new AtomicLong(counterStart());

    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Path("/join")
    public Response join(@QueryParam("token") long token) {
        log.info("Joining user with token {}...", token);
        User user = UserDao.getInstance().getByToken(Database.session(), token);
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Invalid token!\n").build();
        }

        long counterValue = numConnections.getAndIncrement();
        long sessionId = counterValue / 4;
        if (counterValue % 4 == 0) {
            log.info("Started new game session {}!", sessionId);
        }
        if (counterValue % 4 == 3) {
            log.info("Game session {} is ready!", sessionId - 1);
        }
        String url;
        try {
            url = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            url = "localhost";
            e.printStackTrace();
        }
        url = url + ":8090" + "/gs/" + sessionId;

        return Response.ok(url).build();
    }

    @POST
    @Consumes("application/json")
    @Path("/finish")
    public Response finish(String json) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            JsonObject jobj = new Gson().fromJson(json, JsonObject.class);

            int gameSessionId = jobj.get("id").getAsInt();
            JsonObject results = jobj.get("result").getAsJsonObject();
            boolean sessionIsAlreadyFinished = !session.createNativeQuery(
                    "select gameId from bomber.result where gameId=:gameId")
                    .setParameter("gameId", gameSessionId)
                    .getResultList().isEmpty();
            if (sessionIsAlreadyFinished) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Game session " + gameSessionId + " is already finished!").build();
            }
            for (Map.Entry<String, JsonElement> result : results.entrySet()) {
                User user = UserDao.getInstance().getByName(session, result.getKey());
                if (user == null) {
                    txn.rollback();
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Invalid user name " + result.getKey() + "!").build();
                }
                session.createNativeQuery("insert into bomber.result values (:gameId, :userId, :result);")
                        .setParameter("gameId", gameSessionId)
                        .setParameter("userId", user.getId())
                        .setParameter("result", result.getValue().getAsInt())
                        .executeUpdate();
            }

            txn.commit();

            return Response.ok().build();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed!").build();
        }
    }

    /*@GET
    @Produces("application/json")
    @Path("/games")
    public Response games() {
        String usersJson = TokenStore.getAllLoginedUsers().stream().map(User::toJson)
                .collect(Collectors.joining(", ", "{\"users\" : [", "]}"));
        return Response.ok(usersJson).build();
    }*/
}
