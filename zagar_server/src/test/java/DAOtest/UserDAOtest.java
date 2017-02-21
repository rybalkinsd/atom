package DAOtest;

import accountserver.authDAO.UserDAO;
import accountserver.authInfo.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by User on 05.11.2016.
 */
public class UserDAOtest {

    UserDAO userDAO = new UserDAO();
    User user =  new User("Andrew1","1234","a@m.ru");

    @Test
    public void testGetAll(){
        System.out.println(userDAO.getAll());
    }

    @Test
    public void  insertTest(){
        userDAO.insert(user);
        assertTrue(userDAO.getAll().contains(user));
    }

    @Test
    public void deleteTest(){
        userDAO.delete(user);
        assertFalse(userDAO.getAll().contains(user));
    }

    @Test
    public void updateTest(){
        userDAO.insert(user);
        int id = userDAO.getAllWhere("login = \'" + user.getLogin() + "\'").get(0).getId();
        assertTrue(userDAO.update(user.setLogin("Alex")));
        int newId = userDAO.getAllWhere("login = \'" + user.getLogin() + "\'").get(0).getId();
        assertEquals(id, newId);
    }

    @Test
    public void getAllWhereTest(){
        userDAO.insert(user);
        assertEquals(userDAO.getAllWhere("login = \'" + user.getLogin() + "\'").get(0), user);
        userDAO.delete(user);
    }

    @Test
    public void getUserByIdTest(){
        userDAO.insert(user);
        int id = userDAO.getAllWhere("login = \'" + user.getLogin() + "\'").get(0).getId();
        assertEquals(user, userDAO.getUserById(id));
        userDAO.delete(user);
    }

    @Test
    public void getUserIdTest(){
        userDAO.insert(user);
        assertNotEquals(0, userDAO.getUserId(user));
        userDAO.delete(user);
    }


}
