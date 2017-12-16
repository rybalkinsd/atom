package gs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {
    @Autowired
    private GameService gameService;

    @Test
    public void create() throws Exception {
        long id = new GameSession(4).getId();
        assertEquals(id + 1, gameService.create(4));
    }
}
