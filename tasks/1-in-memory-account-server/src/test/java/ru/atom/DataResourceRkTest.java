package ru.atom;


import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;


/**
 * Created by pavel on 27.03.17.
 */
@Ignore
public class DataResourceRkTest {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";


    @Test
    public void usersTest() throws IOException {
        Response response = HttpClientRk.users();
        Assert.assertTrue(response.code() == 200);
    }
}
