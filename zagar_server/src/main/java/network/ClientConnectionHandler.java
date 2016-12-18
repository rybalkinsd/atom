package network;

import network.JSONHelper.JSONHelper;
import network.handlers.PacketHandlerAuth;
import network.handlers.PacketHandlerEjectMass;
import network.handlers.PacketHandlerMove;
import network.handlers.PacketHandlerSplit;
import com.google.gson.JsonObject;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.jetbrains.annotations.NotNull;
import protocol.*;


public class ClientConnectionHandler extends WebSocketAdapter {
    private final static @NotNull
    Logger log = LogManager.getLogger(ClientConnectionHandler.class);

    @Override
    public void onWebSocketConnect(@NotNull Session sess) {
        super.onWebSocketConnect(sess);
        log.info("Socket connected: " + sess);
    }

    @Override
    public void onWebSocketText(@NotNull String message) {
        super.onWebSocketText(message);
        if (getSession().isOpen()) {
            handlePacket(message);
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, @NotNull String reason) {
        ClientConnections clientConnections = ApplicationContext.get(ClientConnections.class);
        Player p = clientConnections.getConnectedPlayer(getSession());
        int index =  ApplicationContext.get(MatchMaker.class).getPlayerSession().get(p.getId());
        ApplicationContext.get(MatchMaker.class).getActiveGameSessions().get(index).leave(p);
        clientConnections.removeConnection(clientConnections.getConnectedPlayer(getSession()));
        super.onWebSocketClose(statusCode, reason);
        log.info("Socket closed: [" + statusCode + "] " + reason);
    }

    @Override
    public void onWebSocketError(@NotNull Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }

    public void handlePacket(@NotNull String msg) {
        JsonObject json = JSONHelper.getJSONObject(msg);
        String name = json.get("command").getAsString();
        switch (name) {
            case CommandAuth.NAME:
                new PacketHandlerAuth(getSession(), msg);
                break;
            case CommandEjectMass.NAME:
                new PacketHandlerEjectMass(getSession(), msg);
                break;
            case CommandSplit.NAME:
                new PacketHandlerSplit(getSession(), msg);
                break;
            case CommandMove.NAME:
                new PacketHandlerMove(getSession(), msg);
                break;
        }
    }
}
