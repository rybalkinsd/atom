package ru.atom.dbhackaton.server.mm;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.LoginedUserDao;
import ru.atom.dbhackaton.server.model.LoginedUser;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by konstantin on 19.04.17.
 */
@Path("/mm")
public class ConnectionHandler {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    @Path("/join")
    @GET
    @Consumes("application/x-www-form-urlencoded")
    public Response join(@QueryParam("token") Long token) {

        Session session = Database.session();

        LoginedUser user = LoginedUserDao.getByToken(session, token);

        ThreadSafeQueue.getInstance().offer(new Connection(user.getId(), user.getUser().getLogin()));
        String GameURL = "wtfis.ru:8090/gs/12345";
        return Response.ok(GameURL).build();
    }

    @Path("/finish")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response finish(@FormParam("json") String json) {
        final Gson gson = new Gson();
        //TODO
        log.info("Result");

        return Response.ok().build();
    }
}
