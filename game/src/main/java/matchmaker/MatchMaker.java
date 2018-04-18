package matchmaker;

import matchmaker.monitoring.QueueState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("matchmaker")
public class MatchMaker {

    @Autowired
    private ApplicationContext context;

    private ArrayList<BlockingQueue<String>> playersQueue;

    @Autowired
    private ConcurrentHashMap<String,Long> playersId;

    @Autowired
    private MatchMakerRepository repository;

    private static boolean enabled = false;

    private final List<MatchMakerDaemon> daemonList = new ArrayList<>(RANK_NUMBER);

    private static final int RANK_NUMBER = 4;
    private static final int[] RANK_BORDERS = {10, 20, 30, 40, 50};

    @PostConstruct
    private void preProcessing() {
        playersQueue = new ArrayList<BlockingQueue<String>>();
        for (int i = 0; i < RANK_NUMBER; i++) {
            playersQueue.add((BlockingQueue<String> )context.getBean("getBlockingQueue"));
            MatchMakerDaemon demon = context.getBean(MatchMakerDaemon.class);
            demon.setPlayersQueue(playersQueue.get(i));
            daemonList.add(demon);
            Thread thread = new Thread(demon);
            thread.start();
        }

    }
    /*
     *   curl -X POST -i http://localhost:8080/matchmaker/join -d "name=test"
     * */

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity join(@RequestParam("name") String name) throws InterruptedException {
        int rank = repository.getUserRank(name);
        for (int i = 0; i < RANK_NUMBER; i++) {
            if (rank < RANK_BORDERS[i]) {
                playersQueue.get(i).offer(name);
                break;
            }
        }
        while (!playersId.containsKey(name))
            Thread.sleep(10);
        Long id = playersId.get(name);
        return ResponseEntity.ok().body(id.toString());
    }


    public List<QueueState> getQueueStateList() {
        ArrayList<QueueState> result = new ArrayList<>(RANK_NUMBER);
        for (int i = 0; i < RANK_NUMBER; i++) {
            result.add(new QueueState(RANK_BORDERS[i], daemonList.get(i).getNumberOfWaitingPlayers()));
        }
        return result;
    }
}
