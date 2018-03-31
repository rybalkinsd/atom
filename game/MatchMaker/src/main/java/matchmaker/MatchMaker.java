package matchmaker;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("matchmaker")
public class MatchMaker {

    private static int MAX_NUMBER_OF_PLAYERS = 4;

    @Autowired
    private BlockingQueue<String> playersQueue;
    @Autowired
    private OkHttpClient client;
    @Autowired
    private ConcurrentHashMap<String,Long> playersId;

    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8090";

    @PostConstruct
    public void MatchMaking() throws Exception {
        int numberOfPlayers = 0;
        int index = 0;
        Long id;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request;
        Response response;
        String[] players = new String[MAX_NUMBER_OF_PLAYERS];

        while (!Thread.interrupted()){
            if (!playersQueue.isEmpty()){
                players[index++] = playersQueue.poll(10,null);
                numberOfPlayers++;
            }
            if(numberOfPlayers == MAX_NUMBER_OF_PLAYERS){
                request = new Request.Builder()
                        .post(RequestBody.create(mediaType , "playerCount=" + numberOfPlayers))
                        .url(PROTOCOL + HOST + PORT + "/game/create").build();
                response = client.newCall(request).execute();
                id = new Long(response.toString());
                index = 0;
                numberOfPlayers = 0;
                for(String names: players)
                    playersId.put(names,id);
            }
        }
    }


    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity join(@RequestParam("name") String name) throws InterruptedException{
        playersQueue.add(name);
        while (!playersId.containsKey(name))
            Thread.sleep(1000);
        Long id = playersId.get(name);
        return ResponseEntity.ok(id);
    }


}
