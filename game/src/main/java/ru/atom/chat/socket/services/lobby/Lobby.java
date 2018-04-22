package ru.atom.chat.socket.services.lobby;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.chat.socket.util.SessionsList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lobby {
    public static int MAX_SIZE = 4;

    private CopyOnWriteArrayList<WebSocketSession> players;

    public Lobby() {
        players = new CopyOnWriteArrayList<>();
    }

    public void connect(WebSocketSession session) {
        if (players.size() < MAX_SIZE)
            players.add(session);
    }

    public void broadcast(String text) {
        for (WebSocketSession session : players) {
            try {
                session.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int size() {
        return players.size();
    }

    public double getRating() {
        return 0;
    }

    public List<String> getPlayers() {
        ArrayList<String> usernames = new ArrayList<>();
        players.forEach(player -> usernames.add(SessionsList.finBySession(player)));
        return usernames;
    }
}
