import ru.atom;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
@Path("/data")
public class DataResources {
    private static final Logger log = LogManager.getLogger(DataResources.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response users() {
        log.info("Users request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users", UsersCache.getRegisteredUsers());
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/online")
    public Response online() {
        log.info("Online request");
        Gson gson = new Gson();
        HashMap<String, LinkedList<User>> response = new HashMap<>();
        response.put("users", UsersCache.getLoginedUsers());
        return Response.ok(gson.toJson(response)).build();
    }
}
