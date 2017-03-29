package ru.atom.authserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.atom.entities.TokenContainer;

/**
 * Created by ikozin on 28.03.17.
 */
@Path("/")
public class DataResource {
    @GET
    @Path("/users")
    @Produces("application/json")
    public static Response users() {
        JsonObject root = new JsonObject();
        root.addProperty("users", TokenContainer.getAllUsernames().toString());
        Gson gson = new Gson();
        String json = gson.toJson(root);
        return Response.ok(json).build();
    }
}
