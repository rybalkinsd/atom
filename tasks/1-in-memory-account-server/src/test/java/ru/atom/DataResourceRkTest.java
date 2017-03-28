package ru.atom;


import okhttp3.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


/**
 * Created by pavel on 27.03.17.
 */

public class DataResourceRkTest {

    @Before
    public void setUp() throws Exception {
        HttpServerRk.startServer();
    }

    @Test
    public void usersTest() throws IOException {
        Response response = HttpClientRk.users();
        Assert.assertTrue(response.code() == 200);
    }

    @After
    public void setDown() throws Exception {
        HttpServerRk.stopServer();
    }
}
