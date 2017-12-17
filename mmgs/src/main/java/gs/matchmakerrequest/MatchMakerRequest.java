package gs.matchmakerrequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Component
public class MatchMakerRequest {
    private static final Logger log = LogManager.getLogger(MatchMakerRequest.class);

    private final RestTemplate rest;
    private final HttpHeaders headers;
    private HttpStatus status;

    public MatchMakerRequest() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
    }

    public void sendScore(String jsonString) {
        String uri = "http://localhost:8080/matchmaker/score";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("json", jsonString);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
    }

    public void gameOver(String playerName) {
        String uri = "http://localhost:8080/matchmaker/kick";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("login", playerName);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}

