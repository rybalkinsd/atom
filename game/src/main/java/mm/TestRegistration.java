package mm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class TestRegistration {

    @Autowired
    private PlayersRepository playersRepository;

    @RequestMapping(
            path = "player",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> create(@RequestParam("name") String name) {
        playersRepository.add(new Player(name));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
