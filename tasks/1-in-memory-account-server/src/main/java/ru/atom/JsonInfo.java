package ru.atom;

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
 * Created by alex on 29.03.17.
 */

@Path("/data")
public class JsonInfo {
    private static final Logger log = LogManager.getLogger(JsonInfo.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response users() {
        log.info("Users request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<String>> response = new HashMap<>();
        response.put("users", StorageOfUsers.getRegisteredUsers());
        return Response.ok(gson.toJson(response)).build();
    }
}
