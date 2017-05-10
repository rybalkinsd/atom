package ru.atom.dbhackaton.mm.server;

/**
 * Created by ilnur on 12.04.17.
 */


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.auth.dao.Database;
import ru.atom.dbhackaton.auth.dao.UserDao;
import ru.atom.dbhackaton.mm.dao.ResultDao;
import ru.atom.dbhackaton.mm.model.Connection;
import ru.atom.dbhackaton.mm.model.Result;
import ru.atom.dbhackaton.mm.model.ThreadSafeQueue;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class MatchMakerServices {
    private static final Logger log = LogManager.getLogger(MatchMakerServices.class);
    private static AtomicLong idGenerator = new AtomicLong();

    public Response join(String token) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (UserDao.getInstance().getByToken(session, token) == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Bad token")
                        .build();
            }

            log.info("User {" + UserDao.getInstance().getByToken(session, token).getName() + "} connected");

            ThreadSafeQueue.getInstance().offer(new Connection(token));
            txn.commit();

            String url = "localhost:8095/gs/" + idGenerator.getAndIncrement() + "";
            return Response.ok().entity(url).build();
        } catch (Exception e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Game server has not created").build();
        }
    }


    public Response finish(String result) {
        Transaction txn = null;

        List<Result> results = new LinkedList<>();
        JsonObject json = new JsonParser().parse(result).getAsJsonObject();
        Integer gameId = json.get("id").getAsInt();

        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            if (!ResultDao.getInstance().getByGameId(session, gameId).isEmpty()) {
                txn.rollback();
                throw new RuntimeException("GameId already exists");
            }

            JsonObject resultJson = json.getAsJsonObject("result");
            for (Map.Entry<String, JsonElement> entry : resultJson.entrySet()) {
                results.add(new Result(gameId,
                        UserDao.getInstance().getByName(session, entry.getKey()),
                        entry.getValue().getAsInt()));
            }

            for (Result newResult : results) {
                ResultDao.getInstance().insert(session, newResult);
            }
            log.info("Results of game #" + gameId + " added");

            return Response.ok().build();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
