package matchmaker;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class MatchMakerDaemon implements Runnable {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MatchMakerDaemon.class);

    private BlockingQueue<String> playersQueue;
    @Autowired
    private OkHttpClient client;
    @Autowired
    private ConcurrentHashMap<String,Long> playersId;
    @Autowired
    private MatchMakerRepository repository;

    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";
    private static int MAX_NUMBER_OF_PLAYERS = 4;
    private static final long WAIT_TIME = 10_000_000_000L;

    private int numberOfPlayers;

    public void setPlayersQueue(BlockingQueue<String> playersQueue) {
        this.playersQueue = playersQueue;
    }

    @Override
    public void run() {
        numberOfPlayers = 0;
        int index = 0;
        long lastTime = Long.MAX_VALUE;
        long curTime = System.nanoTime();
        Long id;

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request;
        Response response;

        String[] players = new String[MAX_NUMBER_OF_PLAYERS];
        log.info(Thread.currentThread().getName() + " Started!");
        while (!Thread.interrupted()) {

            if (!playersQueue.isEmpty()) {
                try {
                    players[index++] = playersQueue.poll(10_000, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    return;
                }
                numberOfPlayers++;

                if (numberOfPlayers == 1) {
                    lastTime = System.nanoTime();
                }
            }

            if ((numberOfPlayers == MAX_NUMBER_OF_PLAYERS) || (numberOfPlayers > 1)
                    && (((curTime = System.nanoTime()) - lastTime) > WAIT_TIME)) {
                String[] playersInThisSession = new String[numberOfPlayers];
                for (int i = 0; i < numberOfPlayers; i++)
                    playersInThisSession[i] = players[i];

                request = new Request.Builder()
                        .post(RequestBody.create(mediaType , "playerCount=" + numberOfPlayers))
                        .url(PROTOCOL + HOST + PORT + "/game/create")
                        .build();
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return;
                }
                try {
                    id = Long.parseLong(response.body().string());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return;
                }
                index = 0;
                numberOfPlayers = 0;
                for (String names: playersInThisSession)
                    playersId.put(names, id);
                repository.saveGameSession(id, playersInThisSession);

            }
        }
    }

    int getNumberOfWaitingPlayers() {
        return numberOfPlayers + playersQueue.size();
    }
}
