package ru.atom.rk01.resource;

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
        List<User> loginedUsers = userManager.getLoginedUsers();
        //TODO use some libs
        int loginedUssersCount = loginedUsers.size();
        StringBuilder json = new StringBuilder("{ \"users\": [");
        if (loginedUssersCount > 0) {
            json.append(loginedUsers.get(0).getLogin());
        }

        for (int i = 1; i < loginedUssersCount; i++) {
            json.append(", ").append(loginedUsers.get(i).getLogin());;
        }

        json.append("]}");
        log.info("Url users/ status {}",  String.valueOf(200));
        return Response.ok(json.toString()).build();
    }
}
