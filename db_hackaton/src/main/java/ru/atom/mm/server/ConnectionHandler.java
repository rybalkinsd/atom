package ru.atom.mm.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.atom.dao.DatabaseClass;
import ru.atom.mm.InfoGame.Match;
import ru.atom.mm.server.matchmaker.Connection;
import ru.atom.mm.server.matchmaker.ThreadSafeQueue;
import ru.atom.object.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("")
public class ConnectionHandler {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    DatabaseClass dbclass = new DatabaseClass();

    @Path("/join")
    @GET
    @Consumes("application/x-www-form-urlencoded")
    public Response join(@QueryParam("token") String token) {

        User user = dbclass.getUserByToken(token);
        log.info("New user ={" + user.getLogin() + "} join match ");

        ThreadSafeQueue.getInstance().offer(new Connection(user.getIdUser(), user.getLogin()));
        String URL = "wtfis.ru:8090/gs/12345";


        log.info(URL);
        return Response.ok(URL).type("text/plain").build();
    }



    @Path("/result")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response result(@FormParam("json") String json) {
        log.info("New result");
        final Gson gson = new Gson();
        Match match = gson.fromJson(json, Match.class);
        log.info("Result Game{" + match.getGameId() + "}");

        return Response.ok().build();
    }
}
