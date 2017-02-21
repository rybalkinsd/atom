package accountserver.api;

import accountserver.dao.TokenDao;
import accountserver.dao.UserDao;
import accountserver.api.User;
import accountserver.api.Token;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 24.10.2016.
 */
public class TokenContainer{
    private static final Logger log = LogManager.getLogger(Authentication.class);

    public static void init() {
        User admin = new User("admin").setPassword("admin");
        tokenDao = new TokenDao();
        userDao = new UserDao();
        addUser(admin);
        issueToken(getUserByString(admin.getName()), 1L);
    }

    private static UserDao userDao;
    private static TokenDao tokenDao;

    //Adding new user to table
    public static boolean addUser(User user) {
        //Checking users with same name
        List<User> oldUsers = userDao.getAllWhere("name = '" + user.getName() + "'");
        log.info(" size of oldusers with this name is {} ", oldUsers.size());
        if (oldUsers.size() == 0) {
            //if this username is unique then inserting new user row and score row with value = 0
            log.info(" size of oldusers with this name {} is null", user.getName());
            userDao.insert(user);
            return true;
        }
        return false;
    }

    public static User getUserByString (String nick){
        //Searching user from table with this name
        List<User> oldUsers = userDao.getAllWhere("name = '" + nick + "'");
        if (oldUsers.size() == 1){
            return oldUsers.get(0);
        }
        return new User(null).setPassword(null);
    }

    //Creating token for logged user
    static Token issueToken(User user) {
        List<Token> oldTokens = tokenDao.getAllWhere("userId = '" + user.getId() + "'");
        //Returning old token if user is already logged
        if (oldTokens.size() == 1){
            return oldTokens.get(0);
        }
        //Inserting new token row in table and increasing score by 2
        Token newtoken = new Token().setUserId(user.getId());
        tokenDao.insert(newtoken);
        return newtoken;
}


    static Token issueToken(User user, long token) {
        List<Token> oldTokens = tokenDao.getAllWhere("userId = '" + user.getId() + "'");
        //Returning old token if user is already logged
        if (oldTokens.size() == 1){
            return oldTokens.get(0);
        }
        //Inserting new token row in table and increasing score by 2
        Token newtoken = new Token(token).setUserId(user.getId());
        tokenDao.insert(newtoken);
        return newtoken;
    }


    static boolean authenticate(User user,String nick , String password) throws Exception {
        //Checking passwords and username
        log.info("passwords: " + password + " vs " + user.getPassword());
        return (password.equals(user.getPassword())&&(nick.equals(user.getName())));
    }


    public static void validateToken(Token token) throws Exception {
        //Checking input token
        List<Token> tokens = tokenDao.getAllWhere("token = '" + token.getToken() + "'");
        if (tokens.size() == 0) {
            throw new Exception("Token validation exception");
        }
        log.info("Correct token ", token.toString());
    }

    //Removing token for logging out returning username or empty string
    public static String removeToken(Long token){
        List<Token> oldTokens = tokenDao.getAllWhere("token = '" + token + "'");
        String res = "";
        if (oldTokens.size() == 1){
            Token newtoken = oldTokens.get(0);
            List<User> oldUsers = userDao.getAllWhere("id = '" + newtoken.getUserId() + "'");
            tokenDao.delete(newtoken);
            res = oldUsers.get(0).getName();
            return res;
        }
        else{
            return res;
        }

    }

    public static Long getTokenByUsername(String name) throws Exception {
        User user = getUserByString(name);
        List<Token> oldTokens = tokenDao.getAllWhere("userid = '" + user.getId() + "'");
        if (oldTokens.size() == 1) {
            return oldTokens.get(0).getToken();
        }
        throw new Exception();
    }
}

