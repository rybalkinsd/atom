package ru.atom.dbhackaton.mm;


import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.model.Aftermath;

@Path("/")
public class MMservice {
	
	private static final Logger log = LogManager.getLogger(MMservice.class);
	
    @GET
    @Path("/join")
    @Produces("text/plain")
    public Response join() {
    	return Response.ok("wtfis.ru:8090/gs/12345").build();
    }

    @POST
    @Path("/finish")
    public Response finish(String body) {
    	Gson gson = new Gson();
    	Map<?,?> results= gson.fromJson(body, Map.class);
    	Map<?,?> scores = (Map<?, ?>) results.get("result");
		String gameId = (String) results.get("id");
    	for(Object key: scores.keySet()) {
    		Object value = scores.get(key);
    		String username = (String) key.toString();
    		Double result = (Double) value;
    		Integer res = result.intValue();
    		Transaction txn = null;
        	try (Session session = Database.session()) {
        		txn = session.beginTransaction();
        		Aftermath am = new Aftermath(gameId, username, res.toString());
    			session.saveOrUpdate(am);
    			txn.commit();   
    		} catch (Exception e) {
    			log.error("Transaction failed.", e);
    			if (txn != null && txn.isActive()) {
    				txn.rollback();
    			}
            	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User already exists.").build();
    		}
    	}
    	return Response.ok().build();
    }
}
