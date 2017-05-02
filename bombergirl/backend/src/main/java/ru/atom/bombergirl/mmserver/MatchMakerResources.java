package ru.atom.bombergirl.mmserver;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.ResultDao;
import ru.atom.bombergirl.dao.TokenDao;
import ru.atom.bombergirl.dao.UserDao;
import ru.atom.bombergirl.dbmodel.Result;
import ru.atom.bombergirl.dbmodel.Token;
import ru.atom.bombergirl.server.Authorized;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ikozin on 17.04.17.
 */
@Path("/")
public class MatchMakerResources {
    private static Random random = new Random(42);
    private static final Logger log = LogManager.getLogger(MatchMakerResources.class);

    @Authorized
    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/join")
    public static Response join(@QueryParam("user") String user, @QueryParam("token") String strToken) {
        log.info("User {} connected", user);
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            Token token = TokenDao.getInstance().getByStrToken(session, strToken);
            if (token == null) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Not logined").build();
            }
            if (!token.getUser().getName().equals(user)) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("Not such user").build();
            }
            //ThreadSafeQueue.getInstance().offer(new Connection(user));
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception occured.").build();
        }
        return Response.ok("localhost:8085/gs/" + MatchMaker.getIdGame() + "/index.html")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/finish")
    public static Response finish(@FormParam("result") String result) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            /* the method is called by game server, so we assume
               that the existence of users in the db has already
               been checked, and we don't check it there
             */

            List<Result> results = new LinkedList<>();
            JsonObject json = new JsonParser().parse(result).getAsJsonObject();
            Integer gameId = json.get("id").getAsInt();
            if (!ResultDao.getInstance().getByGameId(session, gameId).isEmpty()) {
                txn.rollback();
                return Response.status(Response.Status.BAD_REQUEST).entity("GameId already exists").build();
            }
            JsonObject resultJson = json.getAsJsonObject("result");
            for (Map.Entry<String, JsonElement> entry : resultJson.entrySet()) {
                results.add(new Result(gameId,
                        UserDao.getInstance().getByName(session, entry.getKey()),
                        entry.getValue().getAsInt()));
            }

            for (Result newResult: results) {
                ResultDao.getInstance().insert(session, newResult);
            }
            log.info("Results of game #" + gameId + " added");

            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Exception occured.").build();
        }

        return Response.ok().build();
    }
}
