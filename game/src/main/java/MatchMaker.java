


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.xml.ws.ServiceMode;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.sun.security.ntlm.NTLMException.PROTOCOL;

@Controller
@RequestMapping("/matchmaker")
public class MatchMaker {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":8080";

    private static final int maxPlayersInSession = 4;

    /* need to add logging to database*/
    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    @Autowired
    private GameSessionsRepository gameSessionsRepository;

    @Autowired
    private PlayersRepository playersRepository;

    /* After pressing 'START' button Matchmaker puts player in a game session via /matchmaker/join*/
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        /*Maybe instead of calling functions of Game Service we need to create HTTP requests.
            Looks strange to make requests from server to itself.
         */
        /*need to implement collection or database playersRepository of all registered players.
            LeaderBoards and stats also should be there.
         */
        Player currentPlayer;
        try {
            currentPlayer = playersRepository.get(name);
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int ratingRange = 30;
        GameSession result = null;
        while (((result = gameSessionsRepository.get(
                currentPlayer.getRating() - ratingRange,
                currentPlayer.getRating() + ratingRange)) == null)
                && (ratingRange < 200)) {
            ratingRange += 10;
        }
        if (result == null) {
            GameService.create(result.getID());//need some implemetation
        }
        GameService.connect(currentPlayer, result.getID());//needs implemetation
        if (result.numberOfConnectedPlayers() == result.getMaxPlayers())
            GameService.start(result.getID);//need some implementation
        return currentID;
    }


}
