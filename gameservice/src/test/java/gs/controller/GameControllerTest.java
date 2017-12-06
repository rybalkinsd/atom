package gs.controller;

import gs.GameServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class GameControllerTest {

    /*@Before
    public void startServer() {
        GameServer.startGs();
    }*/

    @Test
    @Ignore
    public void create() {
        GameController controller = new GameController();
        ResponseEntity<Long> entity = controller.create(4);
        assertThat(entity.getBody()).isNotNull();
    }

}
