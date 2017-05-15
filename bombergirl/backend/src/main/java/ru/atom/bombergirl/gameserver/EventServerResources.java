package ru.atom.bombergirl.gameserver;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.bombergirl.dao.Database;
import ru.atom.bombergirl.dao.TokenDao;
import ru.atom.bombergirl.dbmodel.Token;
import ru.atom.bombergirl.mmserver.MatchMaker;
import ru.atom.bombergirl.server.Authorized;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by dmitriy on 15.05.17.
 */
@Path("/")
public class EventServerResources {
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/")
    public static Response gs(@QueryParam("id") String id) {
        return Response.ok().build();
    }
}
