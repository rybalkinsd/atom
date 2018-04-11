package mm.Repo;

import playerdb.Player;

import java.util.ArrayList;
import java.util.Date;

public class GameSession {
    private long id;

    private int maxPlayers;

    private int averageRating;

    private Date timeOfLastAction = new Date();


    private ArrayList<Player> playersInSession = new ArrayList<Player>();

    public GameSession(long id,int maxPlayers) {
        this.averageRating = 1000;
        this.id = id;
        this.maxPlayers = maxPlayers;
    }

    public long getId() {
        return id;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public Date getTimeOfLastAction() { return timeOfLastAction; }

    public boolean isFull() {
        return (playersInSession.size() == maxPlayers);
    }


    public void add(Player player) {
        playersInSession.add(player);
        averageRating = calculateAverageRating();
        timeOfLastAction.setTime(timeOfLastAction.getTime());
    }

    public int numberOfConnectedPlayers() {
        return playersInSession.size();
    }

    private int calculateAverageRating() {
        int sum = 0;
        for (Player p: playersInSession)
            sum += p.getRating();
        return sum / playersInSession.size();
    }

    public String toString() {
        String result = "Session " + id + " avg=" + averageRating + ": ";
        for (Player p: playersInSession) {
            result = result.concat(p.getName() + ", ");
        }
        return result;
    }

}
