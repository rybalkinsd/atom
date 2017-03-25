package ru.atom.http.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.resource.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by zarina on 23.03.17.
 */
@Path("/data/")
public class InfoService {
    private static final Logger log = LogManager.getLogger(InfoService.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public Response list() {
        log.info("List request");
        return Response.ok("{\"users\" : " + AuthService.getAllUsers() + "}").build();
    }
}
