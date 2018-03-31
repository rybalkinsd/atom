package matchmaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("matchmaker")
public class MatchMaker {

    @Autowired
    private BlockingQueue<String> playersQueue;

    @Autowired
    private ConcurrentHashMap<String,Long> playersId;

    @Autowired
    MatchMakerDaemon daemon;

    private static boolean enabled = false;

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity join(@RequestParam("name") String name) throws InterruptedException{
        playersQueue.offer(name);
        if (!enabled){
            enabled = true;
            daemon.run();
        }
        while (!playersId.containsKey(name))
            Thread.sleep(1000);
        Long id = playersId.get(name);
        return ResponseEntity.ok(id);
    }


}
