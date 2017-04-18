package ru.atom.dbhackaton.server.resource;

/**
 * Created by Юля on 29.03.2017.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;
import ru.atom.dbhackaton.server.service.Authorized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import ru.atom.resource.AllTokensHere;
//import ru.atom.resource.Authorized;
//import ru.atom.resource.HashCalculator;
//import ru.atom.resource.Token;
//import ru.atom.resource.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

@Path("/")
public class AuthResource {

    private static final Logger log = LogManager.getLogger(AuthResource.class);
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private static final TokenHolder allTokens = new TokenHolder();
    private static final ArrayList<String> names = new ArrayList<>();


    @POST
    @Path("/register")
    @Consumes("application/x-www-form-urlencoded")
    public Response register(@FormParam("user") String name, @FormParam("password") String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        /*
    	if (name == null || password == null || name.isEmpty() || password.isEmpty()) {
            log.info("Registration is not possible. There are blank fields.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Registration is not possible. There are blank fields.").build();
        }
        if (name.length() > 15) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Too long name, sorry :(")
                    .build();
        }
        log.info("Register user " + name);
        if (users.get(name) == null) {
            log.info("New user " + name);
            User user = new User(name, password);
            users.put(name, user);
            return Response.ok("__,,,^._.^,,,__ CONGRATULATIONS!").build();
        }
        log.info("User exist " + name);
        return Response.status(Response.Status.FORBIDDEN).entity("User already exist ").build();
		*/
    	Transaction txn = null;
    	try (Session session = Database.session()) {
    		txn = session.beginTransaction();
    		User user = new User(name, password);
    		UserDao.getInstance().insert(session, user);
    		txn.commit();
    	} catch (Exception e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User already exists.").build();
    	}
    	
        return Response.ok("gotcha").build();
    	
    }


    @POST
    @Path("/login")
    @Consumes("application/x-www-form-urlencoded")

    public Response login(@FormParam("user") String name, @FormParam("password") String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        /*
    	log.info("Login user " + name);

        User user = users.get(name);
        if (user == null) {
            log.info("User " + name + " not found");
            return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
        }

        if (!Arrays.equals(user.getHash(), HashCalculator.calcHash(password.getBytes()))) {
            log.info("Invalid password for user " + name);
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid password for user").build();
        }

        if (!allTokens.validateToken(allTokens.getToken(name))) {
            Token newToken = new Token();
            while (TokenHolder.validateToken(newToken)) {
                newToken = new Token();
            }
            allTokens.put(name, newToken);
            return Response.ok(newToken.toString()).build();
        }
		*/
        //return Response.ok(allTokens.getToken(name).toString()).build();
    	try (Session session = Database.session()) {
    		User user = UserDao.getInstance().getByName(session, name);
    		if (user == null) {
                return Response.status(Response.Status.FORBIDDEN).entity("No such user.").build();
    		}
    		if (!password.equals(user.getPass())) {
    			return Response.status(Response.Status.FORBIDDEN).entity("Wrong password.").build();
    		}
    		
    	}
    	
        return Response.ok("gotcha").build();
    }


    @Authorized
    @POST
    @Path("/auth/logout")
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String text) {
        /*
    	log.info("Logout this user ");
        Token logoutToken = new Token(text);
        if (allTokens.validateToken(logoutToken)) {
            allTokens.remove(logoutToken);
            return Response.ok("oooook (-_-(-_-)-_-)").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
        */
    	
    	return Response.ok().build();
    }


    @Path("/data/users")
    @GET
    public Response users() {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(allTokens.allUsersOnline());
        json = "{users:" + json + "}";
        log.info("GSON", json);
        return Response.ok(json).build();
    }


}
