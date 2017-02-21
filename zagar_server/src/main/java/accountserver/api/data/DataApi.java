package accountserver.api.data;

import accountserver.database.tokens.TokenDao;
import accountserver.database.users.User;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.json.JSONHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by xakep666 on 13.10.16.
 * <p>
 * Provides REST API for work with users
 */
@Path("/data")
public class DataApi {
    @NotNull
    private static final Logger log = LogManager.getLogger(DataApi.class);

    /**
     * Method retrieves logged in users (with valid tokens) and serializes it to jso
     *
     * @return serialized list
     */
    @GET
    @Produces("application/json")
    @Path("users")
    public Response loggedInUsers() {
        log.info("Logged in users list requested");
        UserInfo ret = new UserInfo();
        ret.users = ApplicationContext.instance().get(TokenDao.class).getValidTokenOwners();
        return Response.ok(JSONHelper.toJSON(ret, new TypeToken<UserInfo>() {
        }.getType())).build();
    }

    public static class UserInfo {
        @Expose
        public List<User> users;
    }
}
