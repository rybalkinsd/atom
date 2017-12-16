package mm;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;


@Component
public class HttpClient {

    OkHttpClient client = new OkHttpClient();

    public String post(String url, String body) throws Exception {

        Request request = new Request.Builder()
                .post(RequestBody.create(MediaType.parse("text/plain"), body))
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}


