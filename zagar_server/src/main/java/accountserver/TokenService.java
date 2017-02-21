package accountserver;

import accountserver.dao.TokenDAO;
import accountserver.dao.TokenHibernate;
import accountserver.dao.exceptions.DaoException;
import accountserver.model.data.Token;
import accountserver.model.data.UserProfile;
import org.jetbrains.annotations.TestOnly;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eugene on 10/28/16.
 *
 * Deals with usersSignedIn hashmap
 */
public class TokenService {
    private final TokenDAO dao = new TokenHibernate();

    public UserProfile getUserByTokenString(String tokenString){
        Token token = dao.getTokenByTokenString(tokenString);
        if (token == null) {
            return null;
        }

        return token.getUser();
    }

    void addUserSession(UserProfile user, Token token) throws DaoException {
        token.setUser(user);
        dao.insert(token);
    }

    void removeUserSession(String tokenString) throws DaoException {
        dao.removeByTokenString(tokenString);
    }

    Collection<UserProfile> users(){
        return dao.getAll().stream().map(Token::getUser)
                .collect(Collectors.toList());
    }

    protected Collection<Token> tokens(){
        return dao.getAll();
    }

    Token getTokenByEmail(String email) throws DaoException {
        List<Token> result = dao.getWhere(String.format("user.email = '%s'", email));
        if(result.size() != 1){
            // TODO report
            return null;
        }
        else return result.get(0);
    }

    public boolean validateToken(String tokenString){
        return getUserByTokenString(tokenString) != null;
    }

    @TestOnly
    public TokenDAO getDao() {
        return dao;
    }
}
