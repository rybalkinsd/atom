package mm;


import mm.Repo.GameSession;
import mm.Service.MatchMaker;
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
import mm.playerdb.dao.Player;

import mm.playerdb.dao.PlayerDbDao;

import java.io.IOException;

@Controller
@RequestMapping("/matchmaker")
public class MatchMakerController {

    private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

    @Autowired
    private MatchMaker matchMaker;

    @Autowired
    private PlayerDbDao playerDbDao;

    /* After pressing 'START' button Matchmaker puts player in a game session via /matchmaker/join*/
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        log.info("Join request. name={}", name);
        Player currentPlayer = playerDbDao.get(name);
        if (currentPlayer == null) {
            return new ResponseEntity<>("Not Registered", HttpStatus.BAD_REQUEST);
        }
        try {
            GameSession result = matchMaker.getSession(currentPlayer);
            return new ResponseEntity<String>(String.valueOf(result.getId()), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Unable to find game", HttpStatus.BAD_REQUEST);
        }
    }

}
