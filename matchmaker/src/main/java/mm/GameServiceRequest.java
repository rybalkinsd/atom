package mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GameServiceRequest {
    private static final Logger log = LogManager.getLogger(GameServiceRequest.class);

    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public GameServiceRequest() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
    }

    public String create(int playerCount) {
        String uri = "http://localhost:8090/game/create";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("playerCount", playerCount);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        return response.getBody();
    }

    public String start(long gameId) {

        String uri = "http://localhost:8090/game/start";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("gameId", gameId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        return response.getBody();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
