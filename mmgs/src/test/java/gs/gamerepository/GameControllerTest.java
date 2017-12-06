package gs.gamerepository;

import gs.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTest {
    @Autowired
    private GameController gameController;

    @MockBean
    private GameService gameService;

    @Test
    public void create() throws Exception {
        given(this.gameService.create(2)).willReturn(2L);
        long response = gameController.create(2).getBody();
        assertEquals(2L, response);
    }

    @Test
    public void start() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        ResponseEntity<Long> response = new ResponseEntity<>((long) 0, headers, HttpStatus.OK);
        assertEquals(response, gameController.create(42));
    }

}
