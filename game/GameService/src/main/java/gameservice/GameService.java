package gameservice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequestMapping("game")
public class GameService {

    private static long numOfGame = 0;
    private static volatile ConcurrentHashMap<Long,Integer> gamesRep= new ConcurrentHashMap<>();

    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity create(@RequestParam("playerCount") int playerCount){
        gamesRep.put(++numOfGame,playerCount);
        return ResponseEntity.ok(numOfGame);
    }

}
