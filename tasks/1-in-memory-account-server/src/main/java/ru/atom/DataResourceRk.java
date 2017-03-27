package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by pavel on 25.03.17.
 */
@Path("/data")
public class DataResourceRk {
    private final Logger logger = LogManager.getLogger(DataResourceRk.class);

    @GET
    @Produces("application/json")
    @Path("/users")
    public Response users() {
        Collection<UserRk> logginedUsers = UserContainerRk.getLogginedUsers();

        if (logginedUsers.isEmpty()) {
            logger.info("UserRk list is showned");
            return Response.ok("{\"users\" : []}").build();
        }

        StringBuilder builder = new StringBuilder(
                "{\"users\" : [");

        for (UserRk user: logginedUsers) {
            builder.append("{")
                    .append(user.getName())
                    .append("}, ");
        }

        builder.delete(builder.length() - 2, builder.length());
        builder.append("]}");
        logger.info("UserRk list is showned");
        return Response.ok(builder.toString()).build();
    }
}
