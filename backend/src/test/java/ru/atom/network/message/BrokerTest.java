package ru.atom.network.message;

import org.eclipse.jetty.websocket.api.*;
import org.junit.Before;
import org.junit.Test;
import ru.atom.model.actor.Pawn;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Player;
import ru.atom.util.V;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by sergey on 2/2/17.
 */
public class BrokerTest {
    private Broker broker;
    private Session session;
    private Player player;
    private Pawn pawn;

    @Before
    public void setUp() throws Exception {
        broker = new Broker();
        session = getMockSession();
        player = new Player("luke");
        pawn = new Pawn(V.ZERO);
        player.setPawn(pawn);
        ConnectionPool.putIfAbsent(session, player);
    }

    @Test
    public void receiveMove() throws Exception {
        String moveMsg = "{\"topic\":\"MOVE\",\"data\":{\"direction\":{\"x\":1.0,\"y\":1.0}}}";
        broker.receive(session, moveMsg);
        V startPosition = pawn.getPosition();
        pawn.tick(10);
        assertThat(pawn.getPosition()).isNotEqualTo(startPosition);
    }

    private static Session getMockSession() {
        return new Session() {
            @Override
            public void close() {
            }

            @Override
            public void close(CloseStatus closeStatus) {
            }

            @Override
            public void close(int statusCode, String reason) {
            }

            @Override
            public void disconnect() throws IOException {
            }

            @Override
            public long getIdleTimeout() {
                return 0;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public WebSocketPolicy getPolicy() {
                return null;
            }

            @Override
            public String getProtocolVersion() {
                return null;
            }

            @Override
            public RemoteEndpoint getRemote() {
                return null;
            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public UpgradeRequest getUpgradeRequest() {
                return null;
            }

            @Override
            public UpgradeResponse getUpgradeResponse() {
                return null;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public void setIdleTimeout(long ms) {
            }

            @Override
            public SuspendToken suspend() {
                return null;
            }
        };
    }

}