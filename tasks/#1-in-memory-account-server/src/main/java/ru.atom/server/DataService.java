package ru.atom.server;

import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by konstantin on 29.03.17.
 */
@Path("/data")
public class DataService {
    private static Logger log = LogManager.getLogger(DataService.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response users() {
        log.info("Users request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users", UserContainer.getRegisteredUsers());
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/online")
    public Response online() {
        log.info("Online request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users",  UserContainer.getLogginedUsers());
        return Response.ok(gson.toJson(response)).build();
    }
}
