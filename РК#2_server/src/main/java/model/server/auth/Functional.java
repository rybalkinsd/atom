package model.server.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.MatchDao;
import model.dao.TokenDao;
import model.dao.UserDao;
import model.dao.leaderboard.LeaderBoardDao;
import model.data.Match;
import model.data.Token;
import model.data.User;

import java.util.List;

/**
 * Created by artem on 05.11.16.
 */
public class Functional {
    public static ObjectMapper mapper;

    public static UserDao userDao;
    public static MatchDao matchDao;
    public static TokenDao tokenDao;
    public static LeaderBoardDao lbDao;
    public static final int N = 3;


    public static final String GET_ALL_WHERE =
            "%s %s '%s'";

    static {
        userDao = new UserDao();
        matchDao = new MatchDao();
        tokenDao = new TokenDao();
        mapper = new ObjectMapper();
        lbDao = new LeaderBoardDao();
    }
    public  static User getAssertUser(String name, String password)throws Exception{
        String query = String.format(GET_ALL_WHERE,  "name" , "=" , name);

        List<User> userList = userDao.getAllWhere(query);

        if (userDao.getAllWhere(query).size()!= 1)
            return null;

        User user = userList.get(0);

        if(password!=null && !user.getPassword().equals(password))
            return null;

        return user;
    }

    public  static User getUser(Token token)throws Exception{
        String query = String.format(GET_ALL_WHERE,  "token" , "=" , token.getId());
        List<Match> matchList = matchDao.getAllWhere(query);

        if(matchList.size()==0){
            return null;
        }else {
            Match match = matchList.get(0);
            query = String.format(GET_ALL_WHERE,  "id" , "=" , match.getUser());

            User user = userDao.getAllWhere(query).get(0);
            return user;
        }
    }

    public static Token getAssertToken(User user)throws Exception{
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


    public static void validateToken(String rawToken) throws Exception {
        Token token  = mapper.readValue(rawToken, Token.class);

        String query = String.format(GET_ALL_WHERE,  "id" , "=" , token.getId());

        List<Token> tokenList = tokenDao.getAllWhere(query);

        if(tokenList.size()==0 || !mapper.writeValueAsString(tokenList.get(0)).equals(mapper.writeValueAsString(token)))
            throw new Exception("Token validation exception");
    }

    public static void addRecord(int user)
    {
        lbDao.insert(user);
    }
}
