package DAOtest;

import accountserver.authDAO.TokenDAO;
import accountserver.authDAO.UserDAO;
import accountserver.authInfo.Token;
import accountserver.authInfo.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 05.11.2016.
 */
public class TokenDAOtest {

    TokenDAO tokenDAO = new TokenDAO();
    UserDAO userDao = new UserDAO();
    User user = new User("Andrew","123","a@m.ru");
    Token token;
    {
        userDao.insert(user);
        token = new Token(userDao.getUserId(user));
    }

    @Test
    public void getAllTest(){
        System.out.println(tokenDAO.getAll());
        userDao.delete(user);
    }

    @Test
    public void insertTest(){
        tokenDAO.insert(token);
        assertTrue(tokenDAO.getAll().contains(token));
        userDao.delete(user);
    }

    @Test
    public void deleteTest(){
        tokenDAO.delete(token);
        assertFalse(tokenDAO.getAll().contains(user));
        userDao.delete(user);
    }

    @Test
    public void getAllWhereTest(){
        tokenDAO.insert(token);
        assertEquals(tokenDAO.getAllWhere("number = \'" + token.getNumber() + "\'").get(0), token);
        tokenDAO.delete(token);
        userDao.delete(user);
    }

    @Test
    public void deleteByStringTokenTest(){
        tokenDAO.insert(token);
        tokenDAO.deleteByStringToken(token.getNumber().toString());
        assertFalse(tokenDAO.getAll().contains(user));
        userDao.delete(user);
    }

    @Test
    public void getUserIdByStringTokenTest(){
        tokenDAO.insert(token);
        assertEquals(user.getId(), tokenDAO.getUserIdByStringToken(token.getNumber().toString()));
        tokenDAO.delete(token);
        userDao.delete(user);
    }


}
