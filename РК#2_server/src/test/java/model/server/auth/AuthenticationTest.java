package model.server.auth;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import model.data.Match;
import model.data.Token;
import model.data.User;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
/**
 * Created by venik on 04.11.16.
 */
public class AuthenticationTest {

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
    public void RegisterTest() {
        try {
            clearRegister("test");
            int before = Functional.userDao.getAll().size();
            miniRegisterTest();
            assertEquals(before + 1, Functional.userDao.getAll().size());

            before = Functional.userDao.getAll().size();
            miniRegisterTest();
            assertEquals(before , Functional.userDao.getAll().size());


        }catch (Exception e){ System.out.println(e);}
    }

    @Test
    public String LoginTest() {
        try {
            String token;
            clearLogin("test");

            int beforeToken = Functional.tokenDao.getAll().size();
            int beforeMatch = Functional.matchDao.getAll().size();
            miniLoginTest();
            assertEquals(beforeToken + 1, Functional.tokenDao.getAll().size());
            assertEquals(beforeMatch + 1, Functional.matchDao.getAll().size());


            beforeToken = Functional.tokenDao.getAll().size();
            beforeMatch = Functional.matchDao.getAll().size();
            token = miniLoginTest();
            assertEquals(beforeToken , Functional.tokenDao.getAll().size());
            assertEquals(beforeMatch , Functional.matchDao.getAll().size());
            return token;
        }catch (Exception e) {System.out.println(e); return null;}
    }

    @Test
    public void LogoutTest() {
        try {
            String tok;
            RegisterTest();
            tok =  LoginTest();
            int beforeToken = Functional.tokenDao.getAll().size();
            int beforeMatch = Functional.matchDao.getAll().size();
            miniLogoutTest(tok);
            assertEquals(beforeToken - 1, Functional.tokenDao.getAll().size());
            assertEquals(beforeMatch - 1, Functional.matchDao.getAll().size());

        }catch (Exception e) {System.out.println(e);}
    }

    private void clearRegister(String name){
        try {
            clearLogin(name);
            String query = String.format(Functional.GET_ALL_WHERE, "name", "=", name);
            List<User> userList = Functional.userDao.getAllWhere(query);
            if (userList.size()!= 0) {
                User user = userList.get(0);
                Functional.userDao.delete(user);
            }

        }catch (Exception e){System.out.println(e);}
    }

    private void miniRegisterTest()throws  Exception{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", "test", "test")
        );
        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        com.squareup.okhttp.Response response = client.newCall(request).execute();
    }

    private void clearLogin(String name){
        try {
            String query = String.format(Functional.GET_ALL_WHERE, "name", "=", name);
            List<User> userList = Functional.userDao.getAllWhere(query);
            User user = userList.get(0);

            query = String.format(Functional.GET_ALL_WHERE, "users", "=", user.getId());
            List<Match> matchList = Functional.matchDao.getAllWhere(query);
            Match match = null;
            if (matchList.size()!=0)
                match = matchList.get(0);

            query = String.format(Functional.GET_ALL_WHERE, "id", "=", match.getToken());
            List<Token> tokenList = Functional.tokenDao.getAllWhere(query);
            Token token = null;
            if (tokenList.size()!=0) {
                token = tokenList.get(0);
                Functional.matchDao.delete(match);
                Functional.tokenDao.delete(token);
            }

        }catch (Exception e){System.out.println(e);}
    }

    private String miniLoginTest()throws  Exception{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", "test", "test")
        );

        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();


        com.squareup.okhttp.Response response = client.newCall(request).execute();
        String re = response.body().string();


        String query = String.format(Functional.GET_ALL_WHERE,  "name" , "=" , "test");
        List<User> userList = Functional.userDao.getAllWhere(query);
        assertEquals(userList.size(),1);
        User user = userList.get(0);

        query = String.format(Functional.GET_ALL_WHERE,  "users" , "=" , user.getId());
        List<Match> matchList = Functional.matchDao.getAllWhere(query);
        assertEquals(matchList.size(),1);
        Match match = matchList.get(0);

        query = String.format(Functional.GET_ALL_WHERE,  "id" , "=" , match.getToken());
        List<Token> tokenList = Functional.tokenDao.getAllWhere(query);
        assertEquals(tokenList.size(),1);

        Token token = tokenList.get(0);

        String rawToken = Functional.mapper.writeValueAsString(token);

        assertTrue(rawToken.equals(re));
        return rawToken;
    }

    private void miniLogoutTest(String tokencur)throws  Exception {
        try {

            String requestUrl = SERVICE_URL + "/auth/logout";
            System.out.println("AAAAAAAAAAA" + tokencur);
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", "Bearer" + tokencur)
                    .build();


            com.squareup.okhttp.Response response = client.newCall(request).execute();
            System.out.println(response.message());
        }catch (Exception e){System.out.println(e + "BBBBBBBBBBB");}
    }

}
