package ru.atom.boot.mm;


import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.atom.thread.mm.Connection;
import ru.atom.thread.mm.ConnectionQueue;

import java.util.concurrent.BlockingQueue;


@Controller
@RequestMapping("/connection")
public class ConnectionController {
    private static final Logger log = LogManager.getLogger(ConnectionController.class);


    /**
     * curl test
     * <p>
     * curl -i -X POST -H "Content-Type: application/x-www-form-urlencoded" \
     * localhost:8080/connection/connect -d 'id=1&name=bomberman'
     */
    @RequestMapping(
            path = "connect",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void connect(@RequestParam("id") long id,
                        @RequestParam("name") String name) {

        log.info("New connection id={} name={}", id, name);
        ConnectionQueue.getInstance().offer(new Connection(id, name));
    }

    /**
     * curl test
     * <p>
     * curl -i localhost:8080/connection/list'
     */
    @RequestMapping(
            path = "list",
            method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String list() {
        BlockingQueue<Connection> queue = ConnectionQueue.getInstance();
        return new Gson().toJson(queue);
    }


}
