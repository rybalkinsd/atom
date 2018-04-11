package mm;


import mm.Repo.GameSession;
import mm.Service.MatchMaker;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import playerdb.Player;
import playerdb.PlayersRepository;

import java.io.IOException;

@Controller
@RequestMapping("/matchmaker")
public class MatchMakerController {

    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    @Autowired
    private PlayersRepository playersRepository = PlayersRepository.createPlayersRepository();

    @Autowired
    private MatchMaker matchMaker = MatchMaker.getInstance();

    /* After pressing 'START' button Matchmaker puts player in a game session via /matchmaker/join*/
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        log.info("Join request. name={}", name);
        Player currentPlayer;
        try {
            currentPlayer = playersRepository.get(name);
            GameSession result = matchMaker.getSession(currentPlayer);
            return new ResponseEntity<String>(String.valueOf(result.getId()), HttpStatus.OK);
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Not Registered", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Unable to find a game", HttpStatus.TOO_MANY_REQUESTS);
        }
    }

}
