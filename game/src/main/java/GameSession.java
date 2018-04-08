import org.springframework.stereotype.Repository;

import java.util.ArrayList;

public class GameSession {
    private long ID;

    private int maxPlayers;

    private int averageRating;


    private ArrayList<Player> playersInSession= new ArrayList<Player>();

    public GameSession(long ID,int maxPlayers) {
        this.ID = ID;
        this.maxPlayers = maxPlayers;
    }

    public long getID() {
        return ID;
    }

    public int getAverageRating() {
        return averageRating;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void add(Player player) {
            playersInSession.add(player);
            averageRating = getAverageRating();
    }

    public int numberOfConnectedPlayers() {
        return playersInSession.size();
    }

    private int calcualteAverageRating() {
        int sum = 0;
        for(Player p: playersInSession)
            sum += p.getRating();
        return sum / playersInSession.size();
    }

}
