package mm;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("matchmaker")
public class ConnectionController {
    private static final Logger log = LogManager.getLogger(ConnectionController.class);

    @Autowired
    Matchmaker matchmaker;


    /**
     * curl test
     * <p>
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" localhost:8080/matchmaker/join -d "name=bomberman"
     */

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> join(@RequestParam("name") String name) {
        log.info(name + " joins");
        Long gameId = matchmaker.join(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<String>(gameId.toString(), headers, HttpStatus.OK);
    }
    /**
     * curl test
     * <p>
     * curl -i localhost:8080/connection/list'
     */
    /*@RequestMapping(
            path = "list",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String list() {
        log.info("Games list request");
        return "Player{" +
                "Name= " + name +
                ", id=" + id +
                '}';
    }*/

}
