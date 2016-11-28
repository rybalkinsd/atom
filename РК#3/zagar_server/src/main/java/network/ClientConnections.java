package network;

import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alpi
 * @since 31.10.16
 */
public class ClientConnections {
  private final static Logger log = LogManager.getLogger(ClientConnections.class);

  private final ConcurrentHashMap<Player, Session> connections = new ConcurrentHashMap<>();

  public Session registerConnection(Player player, Session session) {
    log.info("Connection registered [" + player + "]");
    return connections.putIfAbsent(player, session);
  }

  public boolean removeConnection(Player player) {
    log.info("Connection removed [" + player + "]");
    boolean r = connections.remove(player) != null;
    return r;
  }

  public Set<Map.Entry<Player, Session>> getConnections() {
    return connections.entrySet();
  }
}
