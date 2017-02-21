package accountserver;

import accountserver.database.SessionHolder;
import accountserver.model.data.Token;
import accountserver.model.response.ApiErrors.LoginExistsError;
import accountserver.model.response.ApiRequestError;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eugene on 10/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AccountServiceTest {
    private static final AccountService accountService = new AccountService();
    private static final TokenService tokenService = new TokenService();

    private static final String login = "test";
    private static final String pass = "testpass";

    @Before
    public void registerSomeUsers() throws ApiRequestError {
        try {
            accountService.signUp(login,pass);
        } catch (LoginExistsError ignore) {
        }
    }

    @Test
    public void signUp() throws Exception {
        accountService.signUp("eugene222","testpass");
        try {
            accountService.signUp("eugene222","testpass2");
            fail();
        }
        catch(LoginExistsError ignore){
        }

    }

    @Test
    public void signIn() throws Exception {
        Token token1 = accountService.signIn(login,pass);
        Token token2 = accountService.signIn(login,pass);
        assertEquals(token1, token2);
    }

    @Test
    public void logout() throws Exception {
        Token token = accountService.signIn(login,pass);
        assertTrue(tokenService.validateToken(token.toString()));
        accountService.logout(token.toString());

        SessionHolder.renew();
        assertFalse(tokenService.validateToken(token.toString()));
    }

}