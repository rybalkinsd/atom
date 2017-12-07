package ru.atom;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.atomic.AtomicLong;


public class Connection {

    private long playerId = 0;
    private String playerName;
    private WebSocketSession session;

    public Connection(Long playerId, String name, WebSocketSession session) {
        this.playerName = name;
        this.session = session;
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public long getPlayerId() {
        return this.playerId;
    }

}
