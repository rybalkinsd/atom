package gameserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class GameController {
    private static final Logger log = LogManager.getLogger(GameController.class);
    private static AtomicLong idGenerator = new AtomicLong();

    @RequestMapping(
            path = "game/create",
            method = RequestMethod.POST,
            consumes = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String create(@RequestBody String count, HttpServletResponse response, HttpServletRequest request)
            throws InterruptedException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        log.info("Creating game for ={} players", count);
        long id = idGenerator.getAndIncrement();
        log.info("create/game ID={}", id);
        return Long.toString(id);
    }

    @RequestMapping(
            path = "game/start",
            method = RequestMethod.POST,
            consumes = "text/plain")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String start(@RequestBody String gameId, HttpServletResponse response, HttpServletRequest request)
            throws InterruptedException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        log.info("Starting game GameId={}", gameId);
        return gameId;
    }

    public static void setIdGenerator(long id) {
        idGenerator.set(id);
    }
}
