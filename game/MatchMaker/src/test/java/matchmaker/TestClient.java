package matchmaker;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class TestClient implements Runnable {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestClient.class);


    private   int rank ;
    private static String PROTOCOL = "http://";
    private static String HOST = "localhost";
    private static String PORT = ":8080";
    private OkHttpClient eagerClient = new OkHttpClient();
    OkHttpClient client = eagerClient.newBuilder()
            .readTimeout(15_000, TimeUnit.MILLISECONDS)
            .build();

    /*
    *   curl -X POST -i http://localhost:8080/matchmaker/join -d "name=test"
    * */

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public void run() {
        String name = StringGenerator.generateString();
        Object[] param = {name,rank};
        Long id;
        jdbcTemplate.update("INSERT INTO mm.users (login,rank) VALUES (?,?)",param);

        Response response;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, "name=" + name))
                .url(PROTOCOL + HOST + PORT + "/matchmaker/join")
                .build();

        try {
            response = client.newCall(request).execute();
            Assert.assertTrue(response.code() == 200);
            id = Long.parseLong(response.body().string());
            log.info("id: " + id);
            MatchMakerTest.Start.countDown();
            MatchMakerTest.Start.await();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
