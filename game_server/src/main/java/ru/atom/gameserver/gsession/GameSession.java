package ru.atom.gameserver.gsession;

import ru.atom.gameserver.component.ConnectionHandler;
import ru.atom.gameserver.tick.Ticker;

import java.util.HashMap;
import java.util.Map;

public class GameSession {

    private final Ticker ticker;
    private final GameMechanics gameMechanics;
    private final Replicator replicator;
    private final InputQueue inputQueue;

    private final Map<String, Integer> loginOnIdMap;

    private int playersCnt;

    public GameSession(Long gameId, int playersCnt, ConnectionHandler connectionHandler) {
        this.ticker = new Ticker();
        this.replicator = new Replicator(gameId, connectionHandler);
        this.inputQueue = new InputQueue();
        this.gameMechanics = new GameMechanics(ticker, replicator, inputQueue);
        this.loginOnIdMap = new HashMap<>();

        this.playersCnt = playersCnt;

        //gameMechanics must be last tickable in the list of tickable
        ticker.insertTickable(gameMechanics);
    }

    public MessagesOffering messagesOffering() {
        return inputQueue;
    }

    public void start() {
        new Thread(ticker::gameLoop).start();
    }

    public void stop() {
        ticker.stopGameLoop();
    }

    public void addPlayer(String login) {
        int possess = gameMechanics.addPlayer();
        loginOnIdMap.put(login, possess);
        replicator.writePossess(possess, login);
        if (loginOnIdMap.size() == playersCnt) {
            start();
        }
    }

    /**
     * @param login
     * @return true if the last player was been removed from loginOnIdMap
     */
    public boolean removePlayer(String login) {
        Integer id = loginOnIdMap.remove(login);
        //set pawn with id dead
        return loginOnIdMap.isEmpty();
    }

}
