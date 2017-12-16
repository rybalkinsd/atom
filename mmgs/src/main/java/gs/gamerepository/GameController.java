package gs.gamerepository;


import gs.GameMechanics;
import gs.GameService;
import gs.GameSession;
import gs.network.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("game")
public class GameController {

    @Autowired
    GameService gameService;
    private static GameMechanics gameMechanics;

    public static GameMechanics getGameMechanics() {
        return gameMechanics;
    }

    public static void setGameMechanics(GameMechanics gameMechanics) {
        GameController.gameMechanics = gameMechanics;
    }

    //curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded"
    // localhost:8090/game/create -d "playerCount=1234"
    @RequestMapping(
            path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> create(@RequestParam("playerCount") int playerCount) {
        long gameId = gameService.create(playerCount); //FIXME убрать кол-во игроков?
        if (gameMechanics == null) {
            gameMechanics = new GameMechanics(GameRepository.getMap().get(gameId));
            gameMechanics.setGs(GameRepository.getMap().get(gameId));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<Long>(gameId, headers, HttpStatus.OK);
    }

    //curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" localhost:8090/game/start -d "gameId=1"
    @RequestMapping(
            path = "start",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> start(@RequestParam("gameId") long gameId) {
        new Thread(gameMechanics, "game-mechanics-" + gameId).start();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<Long>(HttpStatus.OK);
    }
}
