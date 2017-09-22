package ru.atom.lecture11.billing;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("billing")
public class BillingResource {
    private static Map<String, Integer> userToMoney = new HashMap<>();
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/addUser")
    public Response addUser(@FormParam("user") String user,
                            @FormParam("money") Integer money) {
        if (user == null || money == null) {
            return Response.status(405).build();
        }
        userToMoney.put(user, money);
        return Response.ok("Successfully created user [" + user + "] with money " + userToMoney.get(user) + "\n")
                .build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/sendMoney")
    public Response sendMoney(@FormParam("from") String fromUser,
                              @FormParam("to") String toUser,
                              @FormParam("money") Integer money) {
        if (fromUser == null || toUser == null || money == null) {
            return Response.status(405).build();
        }
        if (!userToMoney.containsKey(fromUser) || !userToMoney.containsKey(toUser)) {
            return Response.status(401).entity("No such user\n").build();
        }
        if (userToMoney.get(fromUser) < money) {
            return Response.status(401).entity("Not enough money to send\n").build();
        }
        userToMoney.put(fromUser, userToMoney.get(fromUser) - money);
        userToMoney.put(toUser, userToMoney.get(toUser) + money);
        return Response.ok("Send success\n").build();
    }

    @GET
    @Produces("text/plain")
    @Path("/stat")
    public Response getStat() {
        return Response.ok(userToMoney.toString() + "\n").build();
    }
}
