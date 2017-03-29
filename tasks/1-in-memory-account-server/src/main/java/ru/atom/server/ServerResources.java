package ru.atom.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.persons.Token;
import ru.atom.persons.TokenStorage;
import ru.atom.persons.User;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by BBPax on 23.03.17.
 */
@Path("/")
public class ServerResources {
    private static final Logger log = LogManager.getLogger(ServerResources.class);
    private static final List<User> authorized = new ArrayList<>();
    private static final List<String> logins = new ArrayList<>();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/register")
    public Response registration(@DefaultValue("Nobody")@FormParam("user") String userName,
                                 @DefaultValue("withoutIt")@FormParam("password") String userPass) {
        if (logins.contains(userName)) {
            log.info("[" + userName + "] wasn't authorized: such user is already existed");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User with this name is already existed").build();
        }
        authorized.add(new User(userName, userPass));
        logins.add(userName);
        log.info("[" + userName + "] was authorized");
        return Response.ok().build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("auth/login")
    public Response login(@FormParam("user") String userName,
                          @FormParam("password") String userPass) {
        if (!authorized.contains(new User(userName, userPass))) {
            log.info("[" + userName + "] wasn't login: such user is not existed");
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong Name or Password").build();
        }
        Token token = TokenStorage.getToken(new User(userName, userPass));
        log.info("{} was logined with token: {}", userName, token.toString());
        return Response.ok().entity(token.toString()).build();
    }

    @POST
    @LogoutSecured
    @Produces("text/plain")
    //@Consumes("application/x-www-form-urlencoded")
    @Path("auth/logout")
    public Response logout(@HeaderParam("Authorization") String token) {
        try {
            Token inputToken = new Token(token.substring(7));
            log.info("{} with token = {} leaved",
                    TokenStorage.findByToken(inputToken), inputToken);
            TokenStorage.removeToken(inputToken);
        } catch (NullPointerException n) {
            log.info("Illegal statement in field : Authorization. {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("May be you miss something ..?")
                    .build();
        }
        return Response.ok().entity("user successfully leaved").build();
    }

    @GET
    @Produces("application/json")
    @Path("data/users")
    public Response online() {
        Gson gson = new Gson();
        HashMap<String, List<User>> jsonResp = new HashMap<>();
        jsonResp.put("users",  TokenStorage.getOnlineUsers());
        return Response.ok(gson.toJson(jsonResp)).build();
    }

}
