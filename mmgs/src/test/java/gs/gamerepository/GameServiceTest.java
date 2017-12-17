package gs.gamerepository;

import gs.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {
    @Autowired
    private GameService gameService;

    @Test
    public void create() throws Exception {
        long id = new GameRepository().newSession(4);
        assertNotEquals(0, id);
        assertNotEquals(id, gameService.create(4));
    }
}
