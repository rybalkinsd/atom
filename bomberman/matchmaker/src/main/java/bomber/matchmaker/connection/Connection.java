package bomber.matchmaker.connection;

public class Connection {

    private final Integer playerId;
    private final String name;

    public Connection(Integer playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                '}';
    }
}