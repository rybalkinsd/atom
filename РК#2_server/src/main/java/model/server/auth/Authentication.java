package model.server.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.MatchDao;
import model.dao.TokenDao;
import  model.dao.UserDao;
import model.data.Match;
import model.data.Token;
import  model.data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/auth")
public class Authentication {
    private static ObjectMapper mapper;

    private static UserDao userDao;
    private static MatchDao matchDao;
    private static TokenDao tokenDao;

    private static final String GET_ALL_WHERE =
            "%s %s '%s'";

    static {
        userDao = new UserDao();
        matchDao = new MatchDao();
        tokenDao = new TokenDao();
        mapper = new ObjectMapper();
    }

    //curl -i -X POST -H "Contion/x-www-form-urlencoded" -H "Host: localhost:8080" -d "user=1&password=1" "http://localhost:8080/auth/register"
    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("user") String name,
                             @FormParam("password") String password) {


        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            User user = getAssertUser(name, null);

            if (user != null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

            user = new User();

            user.setName(name).setPassword(password);
            userDao.insert(user);

            return Response.ok("User " + user.getName() + " registered.").build();
        }catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }

    //curl -i -X POST -H "Contion/x-www-form-urlencoded" -H "Host: localhost:8080" -d "user=1&password=1" "http://localhost:8080/auth/login"
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response login(@FormParam("user") String name,
                             @FormParam("password") String password) {

        if (name == null || password == null || name.length() == 0 || password.length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            User user = getAssertUser(name, password);
            if (user == null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();

            Token token = getAssertToken(user);

            String tokenJson = mapper.writeValueAsString(token);

            return Response.ok(tokenJson).build();
        }catch(Exception e){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
    }


    // curl -i -X POST -H "Authorization: Bearer{"id":83,"date":1478277630646}" -H "Host: localhost:8080" "http://localhost:8080/auth/logout"
    @Authorized
    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logoutUser(@HeaderParam("Authorization") String rawToken) {
            return Response.ok("logout").build();
    }

    private  User getAssertUser(String name,String password)throws Exception{
        String query = String.format(GET_ALL_WHERE,  "name" , "=" , name);

        List<User> userList = userDao.getAllWhere(query);

        if (userDao.getAllWhere(query).size()!= 1)
            return null;

        User user = userList.get(0);

        if(password!=null && !user.getPassword().equals(password))
            return null;

        return user;
    }

    private Token getAssertToken(User user)throws Exception{
        String query = String.format(GET_ALL_WHERE,  "users" , "=" , user.getId());
        List<Match> matchList = matchDao.getAllWhere(query);

        if(matchList.size()==0){
            Token token =  new Token();
            tokenDao.insert(token);
            Match match = new Match().setUser(user.getId()).setToken(token.getId());
            matchDao.insert(match);

            return token;
        }else {
            Match match = matchList.get(0);
            query = String.format(GET_ALL_WHERE,  "id" , "=" , match.getToken());

            Token token = tokenDao.getAllWhere(query).get(0);
            return token;
        }
    }


    static void validateToken(String rawToken) throws Exception {
        Token token  = mapper.readValue(rawToken, Token.class);

        String query = String.format(GET_ALL_WHERE,  "id" , "=" , token.getId());

        List<Token> tokenList = tokenDao.getAllWhere(query);

        if(tokenList.size()==0 || !mapper.writeValueAsString(tokenList.get(0)).equals(mapper.writeValueAsString(token)))
            throw new Exception("Token validation exception");
    }
}
