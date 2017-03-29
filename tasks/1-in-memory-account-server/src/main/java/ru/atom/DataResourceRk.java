package ru.atom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Response users() throws JsonProcessingException {
        Collection<UserRk> logginedUsers = UserContainerRk.getLogginedUsers();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(logginedUsers);
        return Response.ok("{\"users\" : " + json + "}").build();
    }
}
