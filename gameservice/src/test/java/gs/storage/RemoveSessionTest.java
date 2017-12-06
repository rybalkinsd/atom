package gs.storage;

import gs.GameServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RemoveSessionTest {

    @Before
    public void startServer() {
        GameServer.startGs();
    }

    @Test
    @Ignore
    public void removeTest() {
        SessionStorage.addSession(3);
        WebSocketSession session = new WebSocketSession() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public URI getUri() {
                return null;
            }

            @Override
            public HttpHeaders getHandshakeHeaders() {
                return null;
            }

            @Override
            public Map<String, Object> getAttributes() {
                return null;
            }

            @Override
            public Principal getPrincipal() {
                return null;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return null;
            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public String getAcceptedProtocol() {
                return null;
            }

            @Override
            public void setTextMessageSizeLimit(int messageSizeLimit) {

            }

            @Override
            public int getTextMessageSizeLimit() {
                return 0;
            }

            @Override
            public void setBinaryMessageSizeLimit(int messageSizeLimit) {

            }

            @Override
            public int getBinaryMessageSizeLimit() {
                return 0;
            }

            @Override
            public List<WebSocketExtension> getExtensions() {
                return null;
            }

            @Override
            public void sendMessage(WebSocketMessage<?> message) throws IOException {

            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseStatus status) throws IOException {

            }
        };

        SessionStorage.addByGameId(0, session);
        assertTrue(SessionStorage.removeSession(session));
        assertFalse(SessionStorage.removeSession(session));

    }

}
