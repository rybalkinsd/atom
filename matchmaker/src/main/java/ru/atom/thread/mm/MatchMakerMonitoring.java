package ru.atom.thread.mm;


public class MatchMakerMonitoring {
    private int playerInQueue;

    public int getMaxPlayersInGame() {
        return GameSession.PLAYERS_IN_GAME;
    }

    public int getPlayersInQueue() {
        return playerInQueue;
    }

    public String toString() {
        return "PlayerInQueue=" + playerInQueue;
    }

    public void incrementQueue(int i) {
        playerInQueue += i;
    }

    public void decrimentQueue(int d) {
        playerInQueue -= d;
    }

}
