package model.server.api;

import javax.ws.rs.Path;

import model.dao.leaderboard.LeaderBoardDao;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import model.data.LeaderBoardRecord;
import model.server.auth.Authorized;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by svuatoslav on 11/6/16.
 */

@Path("/data")
public class LeaderBoardProvider {
    private static final Logger log = LogManager.getLogger(LeaderBoardProvider.class);
    private static LeaderBoardDao lbDao = new LeaderBoardDao();
    static private final int N = 3;

    @GET
    @Produces("application/json")
    @Path("leaderboard")
    public Response getLeaders() {
        log.info("Top {} players requested.", N);
        try {
            String records = lbDao.getN(N);
            return Response.ok(records).build();
        } catch (Exception e) {
            log.error("Get top {} players failure.", N, e);

        }
        return Response.serverError().build();
    }

    public static void addRecord(String name)
    {
        lbDao.insert(name);
    }
}
