package model.dao;

import model.dao.leaderboard.LeaderBoardDao;
import org.junit.Test;

import static java.lang.System.out;

/**
 * Created by svuatoslav on 11/6/16.
 */
public class LeaderBoardTest {
    private LeaderBoardDao lbDao=new LeaderBoardDao();

    @Test
    public void insertTest()
    {
        lbDao.insert("admin");
    }
    @Test
    public void getTest ()
    {
        String res = "";
        try{
        res =lbDao.getN(2);}
        catch (Exception e){};
        out.println(res);
    }

    @Test
    public void addPointsTest()
    {
        lbDao.addPoints("aaa",10);
    }
}
