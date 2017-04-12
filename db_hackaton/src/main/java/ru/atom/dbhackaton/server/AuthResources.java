package ru.atom.dbhackaton.server;

import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;
import ru.atom.dbhackaton.User;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.UserDao;

import static ru.atom.dbhackaton.MyLogger.getLog;
import static ru.atom.dbhackaton.WorkWithProperties.getStrBundle;


/**
 * Class provides auth operations.
 * @author Western-Co
 */
@Path("auth")
public class AuthResources {
    public AuthResources() {}

    @POST
    @Consumes({"application/x-www-form-urlencoded"})
    @Path("/register")
    public Response register(@FormParam("user") String name,
                             @FormParam("password") String password) {
        try {
            if (name == null || password == null) {
                return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("miss.param")).build();
            } else if (name.length() < 1) {
                return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.short")).build();
            } else if (name.length() > 20) {
                return Response.status(Status.BAD_REQUEST).entity(getStrBundle().getString("name.too.long")).build();
            } else {
                Session session = Database.session();
                if (UserDao.getInstance().getByName(session, name) != null) {
                    getLog().warn(getStrBundle().getString("already.registered"));
                    return Response.status(Status.FORBIDDEN).entity(getStrBundle().getString("already.registered")).build();
                } else {
                    regiterUser(name, password);
                    return Response.ok(getStrBundle().getString("registered")).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

