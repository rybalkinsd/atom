package ru.atom.mm.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.atom.dao.DatabaseClass;
import ru.atom.mm.server.matchmaker.Connection;
import ru.atom.mm.server.matchmaker.ThreadSafeQueue;
import ru.atom.object.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

/*import com.google.gson.Gson;*/

@Path("")
public class ConnectionHandler {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    DatabaseClass dbclass = new DatabaseClass();

    @Path("/join")
    @GET
    @Consumes("application/x-www-form-urlencoded")
    public Response join(@QueryParam("name") String name,
                         @QueryParam("token") String token) {
        log.info("Тут");
        User user = dbclass.getUserByToken(token);
        log.info("New user ={" + user.getLogin() + "} join match with name{" + name
                + "}");
        ThreadSafeQueue.getInstance().offer(new Connection(user.getIdUser(), name));
        String URL = "wtfis.ru:8090/gs/12345";
        log.info(URL);
        /*  new URI().resolve(*/ //
        return Response.ok(URL).type("text/plain").build();
    }


    /* @Authorized*/
    @Path("/finish")
    @POST
    @Consumes("application/json")
    public Response connect(@FormParam("id") long id,
                            @FormParam("name") String name) {


        return Response.ok().build();
    }
}
