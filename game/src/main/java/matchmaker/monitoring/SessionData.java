package matchmaker.monitoring;

import java.util.Date;
import java.util.List;

/**
 * Created by imakarycheva on 08.04.18.
 */
public class SessionData {
    private final long sessionId;
    private final Date startDateTime;
    private final List<String> players;

    public SessionData(long sessionId, Date startDateTime, List<String> players) {
        this.sessionId = sessionId;
        this.startDateTime = startDateTime;
        this.players = players;
    }

    public void addPlayer(String login) {
        players.add(login);
    }

    public long getSessionId() {
        return sessionId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public List<String> getPlayers() {
        return players;
    }
}
