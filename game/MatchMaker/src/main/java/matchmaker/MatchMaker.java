package matchmaker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("matchmaker")
public class MatchMaker {

    @Autowired
    private BlockingQueue<String> playersQueue;

    @Autowired
    private ConcurrentHashMap<String,Long> playersId;

    @Autowired
    private MatchMakerRepository repository;

    private static boolean enabled = false;

    /*
     *   curl -X POST -i http://localhost:8080/matchmaker/join -d "name=test"
     * */

    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity join(@RequestParam("name") String name) throws InterruptedException{
        Document document;
        playersQueue.offer(name);
        int rank = repository.getUserRank(name);
        while (!playersId.containsKey(name))
            Thread.sleep(10);
        Long id = playersId.get(name);
        return ResponseEntity.ok().body(id.toString());
//        try {
//            File file = new File("src/main/resources/static/connection.html");
//            document = Jsoup.parse(file , "UTF-8");
//            Element element = document.getElementById("id");
//            element.text(id.toString());
//            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(document.toString());
//        } catch (IOException e) {
//            System.out.println("Failed to parse HTML!");
//            return ResponseEntity.badRequest().build();
//        }
    }


}
