package model.server.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.MatchDao;
import model.dao.TokenDao;
import model.dao.UserDao;
import model.data.Match;
import model.data.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 05.11.16.
 */





@Path("data")
public class Info {

    @GET
    @Path("users")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response showing() {
        try {
            String query;
            List<Match> matchlist = Functional.matchDao.getAll();
            ArrayList <User> userlist = new ArrayList<>();
            for (Match match : matchlist) {
                query = String.format(Functional.GET_ALL_WHERE,  "id" , "=" , match.getUser());
                userlist.add(Functional.userDao.getAllWhere(query).get(0));

            }
            for (User user : userlist) {
                user.setPassword("");
            }
            String allUsersJSON = Functional.mapper.writeValueAsString(userlist);
            return Response.ok(allUsersJSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}

