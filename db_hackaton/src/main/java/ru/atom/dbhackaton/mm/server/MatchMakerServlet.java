package ru.atom.dbhackaton.mm.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.auth.server.Authorized;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class MatchMakerServlet {
    private static final Logger log = LogManager.getLogger(MatchMakerServlet.class);
    private static final MatchMakerServices services = new MatchMakerServices();

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    @Path("/join")
    public static Response join(@HeaderParam("Authorization") String tokenParam) {
        String token = tokenParam.substring("Bearer".length()).trim();

        Response response;
        response = services.join(token);

        log.info("User with token  " + token + " join on server " + response);

        return response;
    }

    @Authorized
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/finish")
    public static Response finish(@FormParam("result") String result) {
        Response response = services.finish(result);
        return response;
    }


}