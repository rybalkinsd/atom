package ru.atom;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

/**
 * Created by Fella on 28.03.2017.
 */
public class UsersTest {
    private User lera = new User("Lera","Lera1234");
    private User igor = new User("Igor","Igor4321");


    @Before
    public void setUp() throws Exception {
        Users.put(lera);
        Users.put(igor);

    }


    @Test
    public void isContains() throws Exception {
        Users.isContainsName(lera.getLogin());
        assertTrue(Users.isContainsName(lera.getLogin()));
    }
}
