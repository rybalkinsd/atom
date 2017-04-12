/*package ru.atom;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by serega on 26.03.17.
 */
/*@Path("/data")
public class DataResources {
    private static final Logger log = LogManager.getLogger(DataResources.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response users() {
        log.info("Users request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users",  UsersCache.getRegisteredUsers());
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/online")
    public Response online() {
        log.info("Online request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users",  UsersCache.getLoginedUsers());
        return Response.ok(gson.toJson(response)).build();
    }
}*/
