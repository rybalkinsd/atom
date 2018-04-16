package matchmaker.monitoring;

/**
 * Created by imakarycheva on 08.04.18.
 */
public class UserData {
    private final int id;
    private final String login;
    private final int rank;
    private final long gamesPlayed;

    public UserData(int id, String login, int rank, long gamesPlayed) {
        this.id = id;
        this.login = login;
        this.rank = rank;
        this.gamesPlayed = gamesPlayed;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public long getGamesPlayed() {
        return gamesPlayed;
    }

    public String getLogin() {
        return login;
    }
}
