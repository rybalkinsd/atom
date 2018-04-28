package mm.playerdb.Controller;

import mm.playerdb.dao.Player;
import mm.playerdb.dao.PlayerDbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Formatter;

@Controller
@RequestMapping("/leaderboards")
public class LeaderBoardsController {
    @Autowired
    private PlayerDbDao playerDbDao = new PlayerDbDao();

    @RequestMapping(
            path = "get",
            method = RequestMethod.GET)
    public ResponseEntity<String> getLeaderBoards() {
        String result = "Leaderboards:\n";
        for(Player player: playerDbDao.getAll()) {
            result = result.concat("" + player.getRating() + " " + player.getName() + "\n");
        }
        return new ResponseEntity<String>(result,HttpStatus.OK);
    }
}
