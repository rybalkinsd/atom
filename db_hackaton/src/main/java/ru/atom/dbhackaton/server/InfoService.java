package ru.atom.dbhackaton.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.resource.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by zarina on 23.03.17.
 */
@Path("/data/")
public class InfoService {
    private static final Logger log = LogManager.getLogger(InfoService.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public Response users() {
        log.info("Users request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users",  AuthService.getAllUsers());
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/online")
    public Response online() {
        log.info("Online request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users",  AuthService.getOnlineUsers());
        return Response.ok(gson.toJson(response)).build();
    }
}
