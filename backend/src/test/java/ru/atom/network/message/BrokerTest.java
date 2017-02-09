package ru.atom.network.message;

import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import ru.atom.SessionMock;
import ru.atom.model.actor.Pawn;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Player;
import ru.atom.util.V;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by sergey on 2/2/17.
 */
public class BrokerTest {
    private Broker broker;
    private Session session;
    private Player player;
    private ConnectionPool connectionPool;
    private Pawn pawn;

    @Before
    public void setUp() throws Exception {
        broker = Broker.getInstance();
        connectionPool = ConnectionPool.getInstance();
        session = new SessionMock();
        player = new Player("luke", session);
        pawn = Pawn.create(V.ZERO);
        player.setPawn(pawn);
        connectionPool.add(session, player);
    }

    @Test
    public void receiveMove() throws Exception {
        String moveMsg = "{\"topic\":\"MOVE\",\"data\":{\"direction\":{\"x\":1.0,\"y\":1.0}}}";
        broker.receive(session, moveMsg);
        V startPosition = pawn.getPosition();
        pawn.tick(10);
        assertThat(pawn.getPosition()).isNotEqualTo(startPosition);
    }

}