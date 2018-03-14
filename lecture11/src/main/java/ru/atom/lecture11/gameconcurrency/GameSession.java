package ru.atom.lecture11.gameconcurrency;

public class GameSession {
    private String gameId;
    private boolean started;

    public GameSession(String gameId) {
        this.gameId = gameId;
    }

    public void start() {
        this.started = started;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "gameId='" + gameId + '\'' +
                ", started=" + started +
                '}';
    }
}
