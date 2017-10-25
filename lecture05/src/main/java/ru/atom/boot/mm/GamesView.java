package ru.atom.boot.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atom.thread.mm.ThreadSafeMap;

/**
 * Created by sergey on 3/15/17.
 */

@Controller
@RequestMapping("/games")
public class GamesView {
    private static final Logger log = LogManager.getLogger(GamesView.class);

    /**
     * curl test
     *
     * curl -i localhost:8080/games
     */
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getView() {
        log.info("View request");
        return ThreadSafeMap.getAll().toString();
    }
}
