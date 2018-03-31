package matchmaker;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class MatchMakerDaemon implements Runnable {

    @Autowired
    private BlockingQueue<String> playersQueue;
    @Autowired
    private OkHttpClient client;
    @Autowired
    private ConcurrentHashMap<String,Long> playersId;

    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8090";
    private static int MAX_NUMBER_OF_PLAYERS = 4;

    @Override
    public void run() {
        int numberOfPlayers = 0;
        int index = 0;
        Long id;

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request;
        Response response;

        String[] players = new String[MAX_NUMBER_OF_PLAYERS];

        while (!Thread.interrupted()){
            try {
                Thread.sleep(200);
            } catch (Exception e){
                return;
            }
            if (!playersQueue.isEmpty()){
                try {
                    players[index++] = playersQueue.poll(10_000, TimeUnit.SECONDS);
                } catch (InterruptedException e){
                    return;
                }
                numberOfPlayers++;
            }
            if(numberOfPlayers == MAX_NUMBER_OF_PLAYERS){
                System.out.println("kek");
                request = new Request.Builder()
                        .post(RequestBody.create(mediaType , "playerCount=" + numberOfPlayers))
                        .url(PROTOCOL + HOST + PORT + "/game/create")
                        .build();
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e){
                    return;
                }
                try {
                    /*System.out.println(response.body().string());*/
                    id = Long.parseLong(response.body().string());
                    System.out.println(id);
                } catch (Exception e){
                    return;
                }
                index = 0;
                numberOfPlayers = 0;
                for(String names: players) {
                    System.out.println("Player:" + names + " id:" + id);
                    playersId.put(names, id);
                }
            }
        }
    }
}
