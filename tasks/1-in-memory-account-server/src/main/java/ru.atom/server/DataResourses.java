package ru.atom.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import ru.atom.client.User;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by salvador on 01.04.17.
 */
@Path("/data")
public class DataResourses {
    private static final Logger log = LogManager.getLogger(DataResourses.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response display() {
        Gson gson = new Gson();
        ConcurrentHashMap<String, ArrayList<String>> response = new ConcurrentHashMap<>();
        response.put("users",  ServerResourses.loggedUsers.returnAllUsers());
        log.info("Request to display users");
        return Response.ok(gson.toJson(response)).build();
    }

}


