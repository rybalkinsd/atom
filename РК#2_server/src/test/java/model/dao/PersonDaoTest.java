package model.dao;

import model.data.Match;
import model.data.Token;
import org.junit.Test;
import model.data.User;

import static org.junit.Assert.assertEquals;

/**
 * Created by venik on 03.11.16.
 */

public class PersonDaoTest {
    private UserDao userDao = new UserDao();
    private TokenDao tokenDao = new TokenDao();
    private MatchDao matchDao = new MatchDao();

    private static final String GET_ALL_WHERE =
            "%s %s '%s'";

    @Test
    public void getAllUserTest() throws Exception {
        System.out.println(userDao.getAll());
    }

    @Test
    public void insertUserTest() throws Exception {
        User daun1 = new User().setName("Vyunnikov_Viktor").setMail("vjunnikov2010@yandex.ru").setPassword("no_Brain_Bitch");
        User daun2 = new User().setName("Orlov_Artem").setPassword("lalka_palka").setMail(null);
        User daun3 = new User().setName("Sviatoslav_Dmitriev").setPassword("Main_Brain").setMail(null);

        int before = userDao.getAll().size();

        userDao.insert(daun1);
        userDao.insert(daun2);
        userDao.insert(daun3);
        assertEquals(before + 3, userDao.getAll().size());
    }

    @Test
    public void findWhereUserTest() throws Exception {
        String query = String.format(GET_ALL_WHERE,  "name" , "=" , "Orlov_Artem");

        System.out.println(userDao.getAllWhere(query));
    }

    @Test
    public void getAllTokenTest() throws Exception {
        System.out.println(tokenDao.getAll());
    }

    @Test
    public void insertTokenTest() throws Exception {
        Token token1 = new Token();
        Token token2 = new Token();
        Token token3 = new Token();

        int before = tokenDao.getAll().size();

        tokenDao.insert(token1);
        tokenDao.insert(token2);
        tokenDao.insert(token3);
        assertEquals(before + 3, tokenDao.getAll().size());
    }

    @Test
    public void findWhereTokenTest() throws Exception {
        String query = String.format(GET_ALL_WHERE,  "id" , "=" , "62");

        System.out.println(tokenDao.getAllWhere(query));
    }

    @Test
    public void getAllMatchesTest() throws Exception {
        System.out.println(matchDao.getAll());
    }

    @Test
    public void insertMatchesTest() throws Exception {
        Match match1 = new Match().setUser(1).setToken(2);
        Match match2 = new Match().setUser(4).setToken(3);
        Match match3 = new Match().setUser(0).setToken(3);

        int before = matchDao.getAll().size();

        matchDao.insert(match1);
        matchDao.insert(match2);
        matchDao.insert(match3);
        assertEquals(before + 3, matchDao.getAll().size());
    }

    @Test
    public void findWhereMatchesTest() throws Exception {
        String query = String.format(GET_ALL_WHERE,  "users" , "=" , "0");

        System.out.println(matchDao.getAllWhere(query));
    }

}