package ru.atom.dbhackaton.server.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.Token;
import ru.atom.model.TokenStorage;
import ru.atom.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by dmitriy on 28.03.17.
 */

@Path("/")
public class DataResources {
    private static final Logger log = LogManager.getLogger(DataResources.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public static Response displayAll() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Collection<User>> hashMap = new HashMap<>();
        hashMap.put("users", TokenStorage.getInstance().values());
        String resultJson = gson.toJson(hashMap);
        log.info(resultJson);
        return Response.ok(resultJson).build();
    }
}
