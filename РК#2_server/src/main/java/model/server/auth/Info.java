package model.server.auth;

import model.data.Match;
import model.data.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 05.11.16.
 */





@Path("data")
public class Info {
    // curl -i -X GET -H "Contion/x-www-form-urlencoded" -H "Host: localhost:8080" "http://localhost:8080/data/users"

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
                user.setPassword("X");
            }
            String allUsersJSON = Functional.mapper.writeValueAsString(userlist);
            return Response.ok(allUsersJSON).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    //curl -i -X GET -H "Contion/x-www-form-urlencoded" -H "Host: localhost:8080" "http://localhost:8080/data/leaderboard"

    @GET
    @Path("leaderboard")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response getLeaders() {
        try {
            String records = Functional.lbDao.getN(Functional.N);
            return Response.ok(records).build();
        } catch (Exception e) {

        }
        return Response.serverError().build();
    }
}

