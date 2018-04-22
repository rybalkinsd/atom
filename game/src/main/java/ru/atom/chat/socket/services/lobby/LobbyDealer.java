package ru.atom.chat.socket.services.lobby;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.chat.user.Player;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LobbyDealer {
    private CopyOnWriteArrayList<Lobby> lobbies;

    LobbyDealer() {
        lobbies = new CopyOnWriteArrayList<>();
    }

    public Lobby connectToLobby(WebSocketSession session, Player player) {
        Lobby goodLobby = findLobby(player);
        if (goodLobby == null) {
            goodLobby = new Lobby();
            lobbies.add(goodLobby);
        }
        goodLobby.connect(session);
        return goodLobby;
    }

    private Lobby findLobby(Player player) {
        // TODO here we can make rating search
        for (Lobby lobby : lobbies) {
            if (lobby.size() < Lobby.MAX_SIZE) {
                return lobby;
            }
        }
        return null;
    }
}
