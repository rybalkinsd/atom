package network;

import com.google.gson.JsonObject;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import network.handlers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.jetbrains.annotations.NotNull;
import protocol.commands.*;
import utils.json.JSONHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientConnectionHandler extends WebSocketAdapter {
    @NotNull
    private final static Logger log = LogManager.getLogger(ClientConnectionHandler.class);

    @NotNull
    private final static Map<String, PacketHandler> handleMap = new ConcurrentHashMap<>();

    static {
        handleMap.put(CommandAuth.NAME, new PacketHandlerAuth());
        handleMap.put(CommandEjectMass.NAME, new PacketHandlerEjectMass());
        handleMap.put(CommandMove.NAME, new PacketHandlerMove());
        handleMap.put(CommandSplit.NAME, new PacketHandlerSplit());
        handleMap.put(CommandWindowSize.NAME, new PacketHandlerWindowSize());
    }

    @Override
    public void onWebSocketConnect(@NotNull Session sess) {
        super.onWebSocketConnect(sess);
        log.info("Socket connected: " + sess);
    }

    @Override
    public void onWebSocketText(@NotNull String message) {
        super.onWebSocketText(message);
        log.info("Received packet: " + message);
        if (getSession().isOpen()) {
            handlePacket(message);
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, @NotNull String reason) {
        super.onWebSocketClose(statusCode, reason);
        log.info("Socket closed: [" + statusCode + "] " + reason);
        ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
        MatchMaker mm = ApplicationContext.instance().get(MatchMaker.class);
        clientConnections.getConnections().forEach((entry) -> {
            if (!entry.getValue().isOpen()) {
                clientConnections.removeConnection(entry.getKey());
                //leave player
                mm.leaveGame(entry.getKey());
            }
        });

    }

    @Override
    public void onWebSocketError(@NotNull Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }

    private void handlePacket(@NotNull String msg) {
        try {
            JsonObject json = JSONHelper.getJSONObject(msg);
            String name = json.get("command").getAsString();
            log.debug("Received command {} in msg {}", name, msg);
            PacketHandler handler = handleMap.get(name);
            if (handler == null) return;
            handler.handle(getSession(), msg);
        } catch (Exception e) {
            log.warn("Handler exception: " + e);
        }
    }
}
