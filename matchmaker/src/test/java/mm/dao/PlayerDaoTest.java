package mm.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class PlayerDaoTest {
    private PlayerDao playerDao;
    private String login;
    private Player player;
    private int playersBeforeTest;

    @Before
    public void setup() throws Exception {
        playerDao = new PlayerDao();
        login = "Alice" + new Random().nextInt(999999);
        player = new Player().setLogin(login);
        playersBeforeTest = playerDao.getAll().size();
        playerDao.insert(player);
    }

    @Test
    public void getAllTest() throws Exception {
        assertTrue(playerDao.getAll().size() > 0);
    }

    @Test
    public void getAllWhereTest() throws Exception {
        List<Player> person = new PlayerDao().getAllWhere("login like 'Al%'");
        assertTrue(person.stream()
                .map(Player::getLogin)
                .anyMatch(s -> s.startsWith(login))
        );
    }

    @Test
    public void insertTest() throws Exception {
        assertEquals(playersBeforeTest + 1, playerDao.getAll().size());
    }
}
