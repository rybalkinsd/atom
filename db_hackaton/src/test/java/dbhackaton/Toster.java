package dbhackaton;



import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
/**
 * Created by ilnur on 23.04.17.
 */


public class Toster {
    private static String name = "poi";
    private static String password = "iop";


    @Test
    public void register() throws IOException {
        Response response = Client.register(name, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();

        Assert.assertTrue(response.code() == 200);
    }


    @Test
    public void login() throws IOException {
        Response response = Client.login(name, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();

        Assert.assertTrue(response.code() == 200);
    }

    @Test
    public void loginfail() throws IOException {
        Response response = Client.login(name, password);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();

        Assert.assertTrue(response.code() == 400);
    }

    @Test
    public void logout() throws IOException {
        Response response = Client.logout(name);
        System.out.println("[" + response + "]");
        String body = response.body().string();
        System.out.println();
        Assert.assertTrue(response.code() == 200);
    }

}
