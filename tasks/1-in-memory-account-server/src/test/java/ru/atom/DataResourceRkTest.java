package ru.atom;


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

    @Test
    public void usersTest() throws IOException {
        Response response = HttpClientRk.users();
        Assert.assertTrue(response.code() == 200);
    }
}
