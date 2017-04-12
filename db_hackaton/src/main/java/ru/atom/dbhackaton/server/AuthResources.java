package ru.atom.dbhackaton.server;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.services.Services;

import static ru.atom.dbhackaton.WorkWithProperties.getStrBundle;


/**
 * Class provides auth operations.
 * @author Western-Co
 */
@Path("")
public class AuthResources {
    private static final Services services = new Services();

    public AuthResources() {}

    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Path("/register")
    public Response register(@FormParam("user") String name,
                             @FormParam("password") String password) {
        if (name == null || password == null) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("miss.param")).build();
        } else if (name.length() < 1) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.short")).build();
        } else if (name.length() > 20) {
            return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.long")).build();
        } else {
            //Session session = Database.session();
            //System.out.println("Hello before");
            services.registerUser(name, password);
        }
        return Response.ok(getStrBundle().getString("registered")).build();
    }
}
