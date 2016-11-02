package network;

import model.Player;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class ClientConnections {
  private final ConcurrentHashMap<Player, Session> connections = new ConcurrentHashMap<>();

  public Session registerConnection(Player player, Session session) {
    return connections.putIfAbsent(player, session);
  }

  public boolean removeConnection(Player player) {
    return connections.remove(player) != null;
  }

  public Set<Map.Entry<Player, Session>> getConnections() {
    return connections.entrySet();
  }
}
