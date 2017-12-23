package gs.network;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MatchmakerClient {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MatchmakerClient.class);
    private static final String URI = "http://localhost:8090/game";
    private RestTemplate rest;
    private HttpHeaders headers;

    public MatchmakerClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
    }

    public String createPost(int playerCount) {
        String uri = URI + "/create";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("playerCount", playerCount);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        logger.info("Create request");
        return response.getBody();
    }
}
