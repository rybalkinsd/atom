package matchmaker.monitoring;

import matchmaker.MatchMaker;
import matchmaker.MatchMakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by imakarychev on 08.04.18.
 */
@Controller
@RequestMapping("monitoring")
public class MonitoringController {

    @Autowired
    private MatchMaker matchMaker;

    private final MatchMakerRepository repository;

    @Autowired
    public MonitoringController(MatchMakerRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(
            path = "queue",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getPlayers() {
        List<QueueState> queueList = matchMaker.getQueueStateList();
        int lowerRange = 0;
        int num = 1;

        StringBuilder sb = new StringBuilder("<h2>Список очередей</h2><table><tr><th>Номер</th><th>Ранг игроков</th>" +
                "<th>В очереди</th></tr>");

        for (QueueState qs : queueList) {

            sb.append(String.format("<tr><th>%d</th><th>%d - %d</th><th>%d</th></tr>", num, lowerRange,
                    qs.getUpperRange() - 1, qs.getPlayersInQueue()));
            lowerRange = qs.getUpperRange();
            num++;
        }

        sb.append("</table>");

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @RequestMapping(
            path = "sessions",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getSessions() {
        List<SessionData> sessions = repository.getSessionDataList();

        if (sessions.isEmpty()) {
            return new ResponseEntity<>("Сессий не найдено.", HttpStatus.OK);
        }

        StringBuilder sb = new StringBuilder("<h2>Список сессий</h2><table><tr><th>ID</th><th>Дата начала</th>" +
                "<th>Игроки</th></tr>");

        sessions.forEach(s -> {
            sb.append("<tr><th>" + s.getSessionId() + "</th><th>" + s.getStartDateTime() + "</th><th>" +
                    s.getPlayers().stream().collect(Collectors.joining(", ")) + "</th></tr>");
        });
        sb.append("</table>");

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @RequestMapping(
            path = "players",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getUsers() {
        List<UserData> userData = repository.getUserDataList();

        if (userData.isEmpty()) {
            return new ResponseEntity<>("Игроков не найдено.", HttpStatus.OK);
        }

        StringBuilder sb = new StringBuilder("<h2>Список игроков</h2><table><tr><th>ID</th><th>Логин</th>" +
                "<th>Ранг</th><th>Игр сыграно</th></tr>");

        userData.forEach(u -> {
            sb.append("<tr><th>" + u.getId() + "</th><th>" + u.getLogin() + "</th><th>" +
                    u.getRank() + "</th><th>" + u.getGamesPlayed() + "</th></tr>");
        });
        sb.append("</table>");

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    @RequestMapping("")
    public String getHomePage() {
        return "forward:/monitoring/index.html/";
    }
}
