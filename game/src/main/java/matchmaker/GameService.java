package matchmaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("game")
public class GameService {

    @Autowired
    MatchMakerRepository repository;

    private static volatile Long numOfGame = 0L;
    private static volatile ConcurrentHashMap<Long,Integer> gamesRep = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        numOfGame = repository.getLastSessionId() + 1;
    }


    /*
     *  curl -X POST -i http://localhost:8080/game/create -d "playerCount=4"
     * */

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity create(@RequestParam("playerCount") int playerCount) {
        gamesRep.put(++numOfGame,playerCount);
        return ResponseEntity.ok(numOfGame.toString());
    }

}
