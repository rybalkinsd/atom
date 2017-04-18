package ru.atom.mm.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.atom.dao.DatabaseClass;
import ru.atom.mm.server.matchmaker.Connection;
import ru.atom.mm.server.matchmaker.ThreadSafeQueue;
import ru.atom.object.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("")
public class ConnectionHandler {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    DatabaseClass dbclass = new DatabaseClass();

    @Path("/join")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response join(@FormParam("name") String name,
                         @FormParam("token") String token){
        User user = dbclass.getUserByToken(token);
        ThreadSafeQueue.getInstance().offer(new Connection(user.getIdUser(), name));
        URI URL = new URI().resolve("wtfis.ru:8090/gs/12345");

        return Response.ok().contentLocation(URL).build();
    }



   /* @Authorized*/
   @Path("/finish")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response connect(@FormParam("id") long id,
                            @FormParam("name") String name) {


        return Response.ok().build();
    }
}
