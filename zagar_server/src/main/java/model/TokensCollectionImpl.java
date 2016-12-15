package model;

import model.dao.LeaderboardDao;
import model.dao.TokenDao;
import model.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by sl on 19.10.2016.
 */
public class TokensCollectionImpl implements TokensCollection{

    private static final Logger log= LogManager.getLogger(TokensCollectionImpl.class);
    private static final UserDao userDao=new UserDao();
    private static final TokenDao tokenDao=new TokenDao();
    private static final LeaderboardDao leaderboardDao=new LeaderboardDao();

    @Override
    public User getUserByToken(@NotNull Token token) {
        return userDao.findById(token.getId_user()).get();
    }

    private static String makeQuotedString(String s){
        return "'" + s + "'";
    }
    @Override
    public void addUser(@NotNull User user) {
        if (userDao.getAllWhere("name = " + makeQuotedString(user.getName())).contains(user))
            throw new IllegalArgumentException();
        userDao.insert(user);
        if (log.isInfoEnabled())
            log.info(user+"  added to collection !");
    }


    @Override
    public boolean authenticate(User user){
        User maybe=userDao.getAllWhere("email = " + makeQuotedString(user.getName())).get(0);
        if (maybe!=null && maybe.equals(user) && maybe.getPassword().equals(user.getPassword()))
                return true;
        return false;
    }

    @Override
    public Token issueToken(@NotNull User user) {
        user=userDao.getAllWhere("email = " + makeQuotedString(user.getName())).get(0);
        if (user.getId_token()!=null)
            return tokenDao.findById(user.getId_token()).get();
        Token token = Token.MakeToken();
        user.setId_token(token.getToken());
        token.setId_user(user.getId());
        tokenDao.insert(token);
        userDao.update(user);
        return token;
    }

    @Override
    public List<User> getLoginUsers() {
       return userDao.getAllWhere("id_token is not null");
    }


    public void validateToken(String rawToken, int flag, String param) throws Exception {
        Long token = Long.parseLong(rawToken);
        if (!tokenDao.findById(token).isPresent()) {
            throw new Exception("Token validation exception");
        }
        Token t=tokenDao.findById(token).get();
        User from =userDao.findById(t.getId_user()).get();
        log.info("Correct token from '{}'", from);
        switch (flag){
            case 1: remove(t); break; //wona remove
            case 2: ChangeUserName(param, t); break; //wona change name
            case 3: ChangeUserPassword(param, t); break; //wona change pass
            case 4: ChangeUserEmail(param, t); break; //wona change email
        }
    }

    @Override
    public List<LeaderBoard> getNLeaders(int N) {
        return leaderboardDao.getFirstN(N);
    }

    private void ChangeUserEmail(String param, Token t) {
        User u=userDao.findById(t.getId_user()).get();
        if (!userDao.getAllWhere("email = " + makeQuotedString(param)).isEmpty())
            throw new IllegalArgumentException("Such email in use already");
        u.setName(param);
        System.out.println(u);
        userDao.update(u);
    }

    private void ChangeUserPassword(String param, Token t) {
        User u=userDao.findById(t.getId_user()).get();
        u.setPassword(param);
        userDao.update(u);
    }

    private void ChangeUserName(String param, Token token) {
        User u=userDao.findById(token.getId_user()).get();
        u.setNikname(param);
        userDao.update(u);
    }

    private void remove(Token t) {
        User user=userDao.findById(t.getId_user()).get();
        tokenDao.remove(t);
        user.setId_token(null);
        userDao.update(user);
        log.info("User '{}' logged out", user);
    }

}
