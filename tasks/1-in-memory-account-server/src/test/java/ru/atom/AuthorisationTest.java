package ru.atom;
//import javax.ws.rs.core.Response;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.Before;
import ru.atom.client.Client;




/**
 * Created by Fella on 29.03.2017.
 */
public class AuthorisationTest {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    @Before
    public void setUp() throws Exception {
        Response response = Client.newUserRegister("Dimaa","1234gg");
    }

    /* @Test
    public void  register() throws Exception {
       Response response = Client.newUserRegister("ela","1234gg");
       assertEquals(200,response.code());
    }
    @Test
    public void register2() throws Exception{
        assertTrue(Users.isContainsName("ela"));
    }
    */
}
