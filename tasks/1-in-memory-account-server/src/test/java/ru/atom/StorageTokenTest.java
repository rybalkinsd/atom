package ru.atom;


import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Fella on 29.03.2017.
 */
public class StorageTokenTest {

    private User vasya = new User("vasya" , "h345kdll");
    private User lena = new User("lena" , "h345kdlsdrdy4l");
    private User marat = new User("marat" , "h334545kdll");
    private Token tv = new Token();
    private Token tl = new Token();
    private Token tm = new Token();

    @Before
    public void setUp() throws Exception {
        StorageToken.add(tv, vasya);
        StorageToken.add(tl, lena);
    }

    @Test
    public void isContains2() throws Exception {
        assertTrue(StorageToken.isContainsToken(tv));
    }

    @Test
    public void isContains4() throws Exception {
        assertTrue(StorageToken.isContainsUser(vasya));
    }

    @Test
    public void getUsersbyTocen() throws Exception {
        assertEquals(StorageToken.getUserSt(tv), vasya);
    }


    @Test
    public void isContains3() throws Exception {
        StorageToken.add(tm, marat);
        StorageToken.remove(tv);
        assertTrue(!StorageToken.isContainsToken(tv));
    }


    @Test
    public void getTockenByUser() throws Exception {
        assertEquals(StorageToken.getTokenSt(lena), tl);
    }






}
