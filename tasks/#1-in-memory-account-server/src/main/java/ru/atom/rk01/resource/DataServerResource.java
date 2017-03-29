package ru.atom.rk01.resource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ru.atom.rk01.UserManager;
import ru.atom.rk01.User;
import ru.atom.rk01.Authorized;

import java.util.List;


/**
 * Created by dmbragin on 3/28/17.
 */
@Path("/data/")
public class DataServerResource {
    private static final Logger log = LogManager.getLogger(DataServerResource.class);
    private static final UserManager userManager = UserManager.getInstance();


    @Authorized
    @GET
    @Path("users/")
    @Produces("application/json")
    public Response getLoginedUsers() {
        List<String> loginedUsers = userManager.getLoginedUsersNames();
        //TODO use some libs FIX

        String json = new Gson().toJson(loginedUsers);
        JsonObject object = new JsonObject();
        object.addProperty("users", json);


        log.info("Url users/ status {}",  String.valueOf(200));
        return Response.ok(object.toString()).build();
    }
}
