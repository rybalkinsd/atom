package mm;

import org.junit.Ignore;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionControllerTest {
    @Autowired
    private ConnectionController connectionController;

    @MockBean
    private Matchmaker matchmaker;

    //Test for hybernate
    @Ignore
    @Test
    public void join() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        ResponseEntity<String> responseEntity = new ResponseEntity<String>("0", headers, HttpStatus.OK);
        assertEquals(responseEntity, connectionController.join("Alice"));
    }
}
