package ru.atom;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Fella on 29.03.2017.
 */
public class StorageTokenTest {

    User vasya = new User("vasya" , "h345kdll");
    User lena = new User("lena" , "h345kdlsdrdy4l");
    User marat = new User("marat" , "h334545kdll");
    Token tv = new Token(vasya);
    Token rl = new Token(lena);
    Token tm = new Token(marat);

    @Before
    public void setUp() throws Exception {
        StorageToken.add(tv, vasya);
        StorageToken.add(rl, lena);
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
        assertEquals(StorageToken.getTokenSt(lena), rl);
    }







}
