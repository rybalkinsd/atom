package ru.atom.resources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ru.atom.storages.TokenStorage;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;


@Path("/data")
public class DataResource {

    @GET
    @Consumes("application/x-www-form-urlencoded")
    @Path("/users")
    @Produces("application/json")
    public Response users() {
        return Response.ok(getJson(TokenStorage.getOnlineUsers())).build();
    }

    public static String getJson(ArrayList<String> usersArray) {
        Gson gson = new Gson();
        HashMap<String, ArrayList<String>> users = new HashMap<>();
        users.put("users", usersArray);
        return gson.toJson(users);
    }
}
