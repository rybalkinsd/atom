package clientTest;

import client.AuthRequests;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by User on 06.11.2016.
 */
public class ClientTest {

    AuthRequests requests = new AuthRequests();
    String login = "Andrew";
    String password = "123";
    String login2 = "Alex";
    String password2 = "456";

    @Test
    public void registerTest(){
        assertTrue(requests.register(login, password));
        assertFalse(requests.register(login, password));
        assertFalse(requests.register(login, null));
    }

    @Test
    public void loginTest(){
        Long token = requests.login(login, password);
        assertNotEquals(Long.valueOf(0), token);
        assertEquals(token, requests.login(login, password));
    }

    @Test
    public void wrongPasswordTest(){
        requests.login(login, "124");
    }

    @Test
    public void logoutTest(){
        Long token = requests.login(login, password);
        assertTrue(requests.logout(token));
        assertFalse(requests.logout(token));
    }

    @Test
    public void anotherTokenTest(){
        Long token = requests.login(login, password);
        requests.logout(token);
        Long anotherToken = requests.login(login, password);
        assertNotEquals(token, anotherToken);
        requests.logout(anotherToken);
    }

    @Test
    public void changeNameTest(){
        Long token = requests.login("Boris", password);
        assertTrue(requests.changeName(token, login));
        assertEquals( requests.login(login,password), token);
        requests.changeName(token, login);
    }

    @Test
    public void changeEmailTest(){
        Long token = requests.login(login, password);
        assertTrue(requests.changeEmail(token, "b@mail.ru"));
        assertEquals( requests.login(login,password), token);
    }

    @Test
    public void changePasswordTest(){
        Long token = requests.login(login, password);
        assertTrue(requests.changePassword(token, "1234567"));
        assertEquals( requests.login(login,"1234567"), token);
        requests.changePassword(token, password);
    }

    @Test
    public void getUsers(){
        requests.register(login, password);
        requests.register("Alex", "456");
        requests.login(login, password);
        requests.login("Alex","456");
        System.out.println(requests.getUsers());
    }

    @Test
    public void equalsChangeNAmeTest(){
        Long token = requests.login(login2,password2);
        assertFalse(requests.changeName(token,login));
        requests.logout(token);
    }


}
