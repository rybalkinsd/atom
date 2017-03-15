package ru.atom.jersey.mm;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.jersey.aspect.Authorized;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ThreadSafeQueue;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/connect")
public class ConnectionHandler {
    private static final Logger log = LogManager.getLogger(ConnectionHandler.class);

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response connect(@FormParam("id") long id,
                            @FormParam("name") String name) {

        log.info("New connection id={} name={}", id, name);
        ThreadSafeQueue.getInstance().offer(new Connection(id, name));
        return Response.ok("ok").build();
    }
}
