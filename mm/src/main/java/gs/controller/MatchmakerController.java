package gs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("matchmaker")
public class MatchmakerController {

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest()
                    .body("Too short name");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest()
                    .body("Too long name");
        }

        User alreadyLoggedIn = chatService.getLoggedIn(name);
        if (alreadyLoggedIn != null) {
            return ResponseEntity.badRequest()
                    .body("Already logged in");
        }
        chatService.login(name);

        return ResponseEntity.ok().build();
    }
}
