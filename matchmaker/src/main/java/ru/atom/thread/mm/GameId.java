package ru.atom.thread.mm;

public class GameId {
    private final long gameId;

    public GameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "gameId=" + gameId;
    }
}