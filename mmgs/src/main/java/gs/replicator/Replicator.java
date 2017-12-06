package gs.replicator;

import gs.GameSession;
import gs.message.Topic;
import gs.network.Broker;
import gs.network.ConnectionPool;
import org.springframework.web.socket.WebSocketSession;

public class Replicator {

    public void writeReplica(GameSession gs) {
        for (String name : gs.getAllSessions().keySet()) {
            WebSocketSession session = gs.getAllSessions().get(name);
            Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.POSSESS,
                    String.valueOf(gs.getAllPawns().get(name).getId()));
            if (gs.jsonStringBombs() == null) {
                if (gs.jsonStringExplosions() == null) {
                    if (gs.jsonStringBonuses() == null) {
                        Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                                gs.jsonStringWalls() +
                                        "," + gs.jsonStringPawns());
                    } else {
                        Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                                gs.jsonStringWalls() + "," + gs.jsonStringPawns() + ","
                                        + gs.jsonStringBonuses());
                    }
                } else if (gs.jsonStringBonuses() == null) {
                    Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                            gs.jsonStringWalls() + "," + gs.jsonStringExplosions() + ","
                                    + gs.jsonStringPawns());
                } else {
                    Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                            gs.jsonStringWalls() + "," + gs.jsonStringExplosions() + "," + gs.jsonStringPawns()
                                    + "," + gs.jsonStringBonuses());
                }
            } else if (gs.jsonStringExplosions() == null) {
                if (gs.jsonStringBonuses() == null) {
                    Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                            gs.jsonStringWalls() + "," + gs.jsonStringBombs() + "," + gs.jsonStringPawns());
                } else {
                    Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                            gs.jsonStringWalls() + "," + gs.jsonStringBombs() + "," + gs.jsonStringPawns()
                                    + "," + gs.jsonStringBonuses());
                }
            } else {
                if (gs.jsonStringBonuses() == null) {
                    Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                            gs.jsonStringWalls() + "," + gs.jsonStringBombs() + "," + gs.jsonStringExplosions()
                                    + "," + gs.jsonStringPawns());
                } else {
                    Broker.getInstance().send(gs, ConnectionPool.getInstance().getPlayer(session), Topic.REPLICA,
                            gs.jsonStringWalls() + "," + gs.jsonStringBombs() + "," + gs.jsonStringExplosions()
                                    + "," + gs.jsonStringPawns() + "," + gs.jsonStringBonuses());
                }
            }
        }
    }
}
