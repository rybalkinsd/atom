package matchmaker;

import okhttp3.*;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("prototype")
public class TestClient implements Runnable{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Hashtable<Long,Integer> returnedRequests;

    private   int rank ;
    private static String PROTOCOL = "http://";
    private static String HOST = "localhost";
    private static String PORT = ":8080";
    private OkHttpClient client = new OkHttpClient();

    /*
    *   curl -X POST -i http://localhost:8080/matchmaker/join -d "name=test"
    * */

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public void run()  {
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
            if (!returnedRequests.containsKey(id))
                returnedRequests.put(id,1);
            else
                returnedRequests.put(id,returnedRequests.get(id) + 1);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
