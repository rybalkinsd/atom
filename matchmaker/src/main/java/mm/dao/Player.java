package mm.dao;

public class Player {

    private String login;
    private long gameId = 0;

    public Player() {
        this.gameId = 0;
        this.login = "";
    }

    public Player(long gameId, String login) {
        this.gameId = gameId;
        this.login = login;
    }

    public long getGameId() {
        return gameId;
    }

    public Player setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Player setLogin(String login) {
        this.login = login;
        return this;
    }
}
