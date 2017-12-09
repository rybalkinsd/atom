package ru.atom.gameserver.gsession;

import ru.atom.gameserver.component.ConnectionHandler;
import ru.atom.gameserver.tick.Ticker;

public class GameSession {

    private final Ticker ticker;
    private final GameMechanics gameMechanics;
    private final Replicator replicator;
    private final InputQueue inputQueue;

    public GameSession(Long gameId, ConnectionHandler connectionHandler) {
        this.ticker = new Ticker();
        this.gameMechanics = new GameMechanics();
        this.replicator = new Replicator(gameId, connectionHandler);
        this.inputQueue = new InputQueue();
    }

    public MessagesOffering messagesOffering() {
        return inputQueue;
    }

    public void start() {

    }

}
