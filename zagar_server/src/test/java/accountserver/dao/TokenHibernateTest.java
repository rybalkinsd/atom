package accountserver.dao;

import accountserver.database.SessionHolder;
import accountserver.database.TransactionHolder;
import accountserver.model.data.Token;
import accountserver.model.data.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by ivan on 06.11.16.
 */
@SuppressWarnings("NullableProblems")
public class TokenHibernateTest {
    private static final UserProfile user = new UserProfile("testbiuytre","test");
    private static final UserProfile user2 = new UserProfile("testb2ytrew","test");

    @NotNull
    private static TokenHibernate tokendao;
    @NotNull
    private static UserDAO userdao;


    @BeforeClass
    public static void setUp() throws Exception{
        tokendao = new TokenHibernate();
        userdao = new UserProfileHibernate();

        userdao.insert(user);
        userdao.insert(user2);
    }

    @Before
    public void removeTokens() throws Exception {
        TransactionHolder.getTransactionHolder().getSession().createQuery("delete Tokens").executeUpdate();
        TransactionHolder.getTransactionHolder().close();

        SessionHolder.getHolder().getSession().clear();

    }


    @Test
    public final void insert() throws Exception {
        Long id1 = tokendao.insert(new Token(user));
        Long id2 = tokendao.insert(new Token(user2));
        assertEquals(Long.valueOf(id1 + 1L), id2);

        assertEquals(tokendao.getById(id1).getUser().getEmail(), user.getEmail());
        assertEquals(tokendao.getById(id2).getUser().getEmail(), user2.getEmail());
    }

    @Test
    public final void getById() throws Exception {
        Token token = new Token(user);
        Token token2;

        Long id = tokendao.insert(token);

        assertNull(tokendao.getById(-1L));
        assertNotNull(token2 = tokendao.getById(id));
        assertEquals(token, token2);
    }

    @Test
    public final void getWhere() throws Exception {
        Token token = new Token(user);
        Long id = tokendao.insert(token);

        Collection<Token> tokens = tokendao.getWhere(String.format("token.id = %d", id));
        assertNotNull(tokens);
        assertTrue(tokens.contains(token));

        tokens = tokendao.getWhere("token.id = '135'");
        assertFalse(tokens.contains(token));
    }

    @Test
    public final void getAll() throws Exception {

        Token token = new Token(user);
        tokendao.insert(token);
        Collection<Token> tokens = tokendao.getAll();
        int size = tokens.size();
        assertTrue(tokens.contains(token));

        Token token1 = new Token(user2);
        tokendao.insert(token1);
        tokens = tokendao.getAll();
        assertEquals(tokens.size(), size + 1);
        assertTrue(tokens.contains(token1));
        assertTrue(tokens.contains(token));
    }

    @Test
    public final void getTokenByTokenString() throws Exception {

        Token token = new Token(user);
        tokendao.insert(token);

        String tokenString = token.toString();
        Token token1 = tokendao.getTokenByTokenString(tokenString);

        assertEquals(token, token1);
    }

    @Test
    public final void remove() throws Exception {

        Token token = new Token(user);
        tokendao.insert(token);

        tokendao.remove(token);

        Collection<Token> tokens = tokendao.getAll();
        assertEquals(tokens.size(), 0);
    }
}


