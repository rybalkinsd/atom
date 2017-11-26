import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class MatchmakerClient {
    private String server = "http://localhost:8090/game";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public MatchmakerClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
    }

    public String get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public String post(String uri, String body) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
