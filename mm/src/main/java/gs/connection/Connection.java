package gs.connection;

public class Connection {
    private long gameId;
    private String name;

    public long getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "gameId=" + gameId +
                ", name='" + name + '\'' +
                '}';
    }
}
