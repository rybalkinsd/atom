package ru.atom.server;


import ru.atom.authfilter.Authorized;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by Fella on 28.03.2017.
 */
@Path("/data/users")
public class InfoJersey {
    @Authorized
    @GET
    @Produces("application/json")
    public Response infoUsers() {

        return Response.ok("Info").build();
    }
}
