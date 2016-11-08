package model.Server.profile;

import com.squareup.okhttp.*;
import model.data.Token;
import model.data.User;
import model.server.auth.AuthenticationTest;
import model.server.auth.Functional;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by artem on 08.11.16.
 */
public class Profiletest {
        private static final String PROTOCOL = "http";
        private static final String HOST = "localhost";
        private static final String PORT = "8080";
        private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

        private static final OkHttpClient client = new OkHttpClient();
        static {
            client.setConnectTimeout(1, TimeUnit.MINUTES);
            client.setReadTimeout(1, TimeUnit.MINUTES);
            client.setWriteTimeout(1, TimeUnit.MINUTES);
        }

   @Test
   public void Nametest() {
       try {
       String token;
       AuthenticationTest at = new AuthenticationTest();
           at.RegisterTest();
           token = at.LoginTest();
           miniNameTest(token, "tester");
           miniNameTest(token,"test");
       } catch (Exception e) {
           System.out.println("Something went wrong" + e);
       }
   }
   private void miniNameTest(String token,String newname){
        try{

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(
                    mediaType,
                    String.format("name=%s", newname)
            );
            String requestUrl = SERVICE_URL + "/profile/name";
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer" + token)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            User user;
            Response response = client.newCall(request).execute();
            user = Functional.getUser(Functional.mapper.readValue(token,Token.class));

            assertEquals(newname, user.getName());

        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void Passwordtest() {
        try {
            String token;
            AuthenticationTest at = new AuthenticationTest();
            at.RegisterTest();
            token = at.LoginTest();
            miniPasswordTest(token, "tester");
            miniPasswordTest(token,"test");
        } catch (Exception e) {
            System.out.println("Something went wrong in getBatch." + e);
        }
    }
    private void miniPasswordTest(String token,String newname){
        try{

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(
                    mediaType,
                    String.format("password=%s", newname)
            );
            String requestUrl = SERVICE_URL + "/profile/password";
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();


            Response response = client.newCall(request).execute();
            User user = Functional.getUser(Functional.mapper.readValue(token,Token.class));
            System.out.println("ABRA");
            assertEquals(newname, user.getPassword());

        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void Mailtest() {
        try {
            String token;
            AuthenticationTest at = new AuthenticationTest();
            at.RegisterTest();
            token = at.LoginTest();
            miniMailTest(token, "tester");
            miniMailTest(token,"test");
        } catch (Exception e) {
            System.out.println("Something went wrong in getBatch." + e);
        }
    }
    private void miniMailTest(String token,String newname){
        try{

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(
                    mediaType,
                    String.format("email=%s", newname)
            );
            String requestUrl = SERVICE_URL + "/profile/email";
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer         " + token)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            User user = Functional.getUser(Functional.mapper.readValue(token,Token.class));
            System.out.println("OLDVALUE" + user.getMailil());
            Response response = client.newCall(request).execute();
            user = Functional.getUser(Functional.mapper.readValue(token,Token.class));
            System.out.println("NEWVALUE" + user.getMailil());

            assertEquals(newname, user.getMailil());

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
