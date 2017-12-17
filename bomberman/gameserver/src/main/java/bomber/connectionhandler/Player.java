package bomber.connectionhandler;

import org.springframework.web.socket.WebSocketSession;

public class Player {
    private long gameid = 0;
    private WebSocketSession webSocketSession = null;
    private String name = null;

    public Player(long gameid, int id, String name) {
        this.gameid = gameid;
        this.name = name;
    }

    public Player() {

    }


    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public long getGameid() {
        return gameid;
    }

    public void setGameid(long gameid) {
        this.gameid = gameid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
