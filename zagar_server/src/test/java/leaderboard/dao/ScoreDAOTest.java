package leaderboard.dao;

import accountserver.dao.exceptions.DaoException;
import leaderboard.model.Score;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eugene on 12/16/16.
 */
public class ScoreDAOTest {


    private static ScoreDAO dao;

    static {
        try {
            dao = new ScoreDAO(0);
        } catch (DaoException e) {
            e.printStackTrace();
            fail();
        }
    }


    @BeforeClass
    public static void flush() throws DaoException {
        dao.flushAll();
    }

    @Test
    public void insert() throws Exception {
        Score score = new Score(0, 1);
        dao.insert(score);

        try {
            // primary key violation
            dao.insert(score);
            fail();
        }
        catch (DaoException ignored){
        }

        int size = dao.getAll().size();
        Score score1 = new Score(1,1);
        dao.insert(score1);

        assertEquals(size + 1, dao.getAll().size());
    }

    @Test
    public void getById() throws Exception {
        Score score = new Score(3, 33);
        dao.insert(score);

        assertEquals(score.getPlayerId(), dao.getById((long) score.getPlayerId()).getPlayerId());
        assertNull(dao.getById(99L));
    }

    @Test
    public void addPoints() throws Exception {
        Score score = new Score(6, 33);
        dao.insert(score);

        dao.addPoints(6L, 10);
        assertEquals(43, dao.getById(6L).getScore());
    }

    @Test
    public void getWhere() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void remove() throws Exception {
        Score score = new Score(4, 33);
        dao.insert(score);

        dao.remove(4L);
        assertNull(dao.getById(4L));
    }

    @Test
    public void flush1() throws Exception {
        Score score = new Score(5, 33);
        dao.insert(score);

        dao.flush();
        assertEquals(0, dao.getAll().size());
    }

}