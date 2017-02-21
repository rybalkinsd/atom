package accountserver;

import accountserver.dao.UserDAO;
import accountserver.dao.UserProfileHibernate;
import accountserver.dao.exceptions.DaoException;
import accountserver.database.TransactionHolder;
import accountserver.misc.CredentialsPolicy;
import accountserver.model.data.Token;
import accountserver.model.data.UserProfile;
import accountserver.model.response.ApiErrors.InternalError;
import accountserver.model.response.ApiErrors.LoginExistsError;
import accountserver.model.response.ApiErrors.PolicyViolationError;
import accountserver.model.response.ApiErrors.WrongCredentialsError;
import accountserver.model.response.ApiRequestError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.TestOnly;

import java.util.Collection;

/**
 * Created by eugene on 10/9/16.
 *
 * Serves all accounting stuff
 * login/logout/register
 */

public class AccountService {
    private static final Logger LOG = LogManager.getLogger("account");

    private final UserDAO dao = new UserProfileHibernate();
    private final TokenService tokenService = new TokenService();


    @TestOnly
    public UserDAO getDao() {
        return dao;
    }

    /**
     *
     * @param email
     * @param password
     * @return Token
     * @throws ApiRequestError
     *
     * 1. checks out users already signed in. if present, returns issued token
     * 2. checks out db
     * 3. if ok, issues new Token\
     * 3'. if not ok, throws "Wrong credentials"
     * 4. adds user session and returns token
     */
    public Token signIn( String email, String password)
            throws ApiRequestError
    {
        try (TransactionHolder ignored = TransactionHolder.getTransactionHolder()){
            Token token = tokenService.getTokenByEmail(email);
            if(null != token) return token;
            UserProfile user = dao.getByEmail(email);
            if(null == user || !user.getPassword().equals(password)) {
                throw new WrongCredentialsError();
            }
            token = new Token(user);
            tokenService.addUserSession(user, token);
            LOG.info("'" + email + "' logged in");

            return token;
        } catch (DaoException e) {
            TransactionHolder.getTransactionHolder().rollback();
            LOG.error(e.getCause().getMessage());
            throw new InternalError();
        }

    }

    /**
     *
     * @param login
     * @param pass
     * @throws ApiRequestError
     *
     * 1. checks db for login existence
     * 2. if ok, check policy violation (empty login, forbidden symbols)
     * 2'. if not ok, throws LoginExistsError
     * 2''. if login violates policy, throws PolicyVoilationError
     * 3. add new user to db
     */

    public void signUp( String login, String pass)
            throws ApiRequestError {

        if(!CredentialsPolicy.checkLogin(login) || !CredentialsPolicy.checkPassword(pass)){
            throw new PolicyViolationError();
        }

        try (TransactionHolder ignored = TransactionHolder.getTransactionHolder()){
            if (dao.getByEmail(login) != null) throw new LoginExistsError(login);
            dao.insert(new UserProfile(login,pass));
        }
        catch (DaoException e) {
            LOG.error(e.getCause().getMessage());
            throw new InternalError();
        }
        LOG.info("'" + login + "' signed up");
    }

    public Collection<UserProfile> getOnlineUsers(){
        return tokenService.users();
    }

    public void logout(String tokenString) throws InternalError {
        try {
            tokenService.removeUserSession(tokenString);
        } catch (DaoException e) {
            LOG.error(e.getCause().getMessage());
            throw new InternalError();
        }
//        LOG.info(String.format("'%s' logged out", profile.getEmail()));
    }


}
