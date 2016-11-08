package model.dao;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;


import model.dao.leaderboard.LeaderBoardDao;
import model.data.LeaderBoardRecord;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import com.google.gson.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.StrictMath.random;
import static org.junit.Assert.*;

/**
 * Created by svuatoslav on 11/8/16.
 */

public class LeaderBoardDaoTest {
    private LeaderBoardDao lbDao = new LeaderBoardDao();
    Random rand = new Random();
    private static final OkHttpClient client = new OkHttpClient();
    static {
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setWriteTimeout(1, TimeUnit.MINUTES);
    }
    @NotNull
    private static Gson gson = new GsonBuilder().create();

    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    @Test
    public void insertDeleteTest(){
        List<Integer> res;
        int buf = ((Double)(rand.nextDouble()*1000000)).intValue();
        int num;
        res = lbDao.getAll();
        num=res.size();
        lbDao.insert(buf);
        res = lbDao.getAll();
        assertEquals(res.size(),num+1);
        assertTrue(res.contains(buf));
        lbDao.delete(buf);
        res = lbDao.getAll();
        assertEquals(res.size(),num);
        assertFalse(res.contains(buf));
    }

    //Drop tables before the test
    @Test
    public void getBestTest() throws Exception{
        String json;
        List<Integer> res;
        int N  = ((Double)(rand.nextDouble()*4+1)).intValue()*2;
        for (int i=0;i<N;i++) {
            TestRegister("Test user "+i);
        }
        res=lbDao.getAll();
        assertEquals(N,res.size());
        json=lbDao.getN(N);
        List<LeaderBoardRecord> reslb=gson.fromJson(json, List.class);
        assertEquals(reslb.size(),N);
        json=lbDao.getN(N/2);
        reslb=gson.fromJson(json, List.class);
        assertEquals(reslb.size(),N/2);
        json=TestGetN();
        reslb=gson.fromJson(json, List.class);
        assertEquals(reslb.size(),3);
    }

    private void TestRegister(String name)throws  Exception{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("user=%s&password=%s", name, "test")
        );
        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        com.squareup.okhttp.Response response = client.newCall(request).execute();
    }

    private String TestGetN()throws  Exception{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestUrl = SERVICE_URL + "/data/leaderboard";
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        com.squareup.okhttp.Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
