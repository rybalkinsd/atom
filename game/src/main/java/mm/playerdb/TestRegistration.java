package mm.playerdb;

import mm.playerdb.dao.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import mm.playerdb.dao.PlayerDbDao;

@Controller
@RequestMapping("/register")
public class TestRegistration {

    @Autowired
    private PlayerDbDao playerDbDao = new PlayerDbDao();

    @RequestMapping(
            path = "player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> register(@RequestParam("name") String name, @RequestParam("pwd") String password) {
        if (playerDbDao.get(name) == null)
            return new ResponseEntity<>("Already registered",HttpStatus.BAD_REQUEST);
        playerDbDao.add(new Player(name,password));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
