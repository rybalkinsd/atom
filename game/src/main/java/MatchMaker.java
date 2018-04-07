
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@Service
@RequestMapping("/matchmaker")
public class MatchMaker {
    private static final int maxPlayersInSession = 4;
    private long currentID = 0;

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
    public long join(@RequestParam("name") String name) {
        /*Maybe instead of calling functions of Game Service we need to create HTTP requests.
            Looks strange to make requests from server to itself.
         */
        /*need to implement collection or database playersRepository of all registered players.
            LeaderBoards and stats also should be there.
         */
        Player currentPlayer = playersRepository.getPlayer(name);
        int ratingRange = 30;
        GameSession result = null;
        while (((result = gameSessionsRepository.get(
                currentPlayer.getRating() - ratingRange,
                currentPlayer.getRating() + ratingRange)) == null)
                && (ratingRange < 200)) {
            ratingRange += 10;
        }
        if (result == null) {
            currentID++;
            gameSessionsRepository.put(result = new GameSession(currentID, maxPlayersInSession));
            GameService.create(result.getID());//need some implemetation
        }
        GameService.connect(currentPlayer, result.getID());//needs implemetation
        if (result.numberOfConnectedPlayers() == maxPlayersInSession)
            GameService.start(currentID);//need some implementation
        return currentID;
    }
}
