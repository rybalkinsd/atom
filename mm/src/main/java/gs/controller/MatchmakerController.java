package gs.controller;

import gs.service.MatchmakerService;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("matchmaker")
public class MatchmakerController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MatchmakerController.class);

    @Autowired
    MatchmakerService matchmakerService;

    private static HttpHeaders headers = new HttpHeaders();
    static {
        headers.add("Access-Control-Allow-Origin", "*");
    }

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        logger.info(name + " joins");
        Long gameId = matchmakerService.join(name);
        return new ResponseEntity<String>(gameId.toString(), headers, HttpStatus.OK);
    }
}
