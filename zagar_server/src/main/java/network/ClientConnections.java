package network;

import model.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class ClientConnections {
    private final ConcurrentHashMap<Player, Session> connections = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Session, Player> players = new ConcurrentHashMap<>();

    @NotNull
    public Session registerConnection(@NotNull Player player, @NotNull Session session) {
        players.putIfAbsent(session, player);
        connections.putIfAbsent(player, session);
        return session;
    }

    public boolean removeConnection(@NotNull Player player) {
        Session session = connections.get(player);
        return connections.remove(player) != null && players.remove(session) != null;
    }

    @NotNull
    public Set<Map.Entry<Player, Session>> getConnections() {
        return connections.entrySet();
    }

    @Nullable
    public Player getPlayerBySession(@NotNull Session session) {
        return players.get(session);
    }

    @Nullable
    public Session getSessionByPlayer(@NotNull Player player) {
        return connections.get(player);
    }
}
