package test.java;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import zagar.Game;
import zagar.auth.AuthClient;
import zagar.network.ServerConnectionSocket;
import zagar.network.packets.PacketEjectMass;
import zagar.network.packets.PacketMove;
import zagar.network.packets.PacketSplit;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class MechanicsPacketSender {
    public String gameServerUrl = "ws://127.0.0.1:7000";
    @NotNull
    public AuthClient authClient = new AuthClient();
    @Test
    public void SendMechanicsMsg () throws Exception {
        authClient.register("zAgar", "pass");
        Game.serverToken = authClient.login("zAgar", "pass");
        Game.login="zAgar";
        Game.socket=new ServerConnectionSocket();
        final WebSocketClient client = new WebSocketClient();
        Thread thread = new Thread(() -> {
            try {
                client.start();
                URI serverURI = new URI(gameServerUrl + "/clientConnection");
                ClientUpgradeRequest request = new ClientUpgradeRequest();
                request.setHeader("Origin", "zagar.io");
                client.connect(Game.socket, serverURI, request);
                Game.socket.awaitClose(7, TimeUnit.DAYS);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
        thread.start();
        while(Game.socket==null || Game.socket.session==null || !Game.socket.session.isOpen()) {}
        //Thread.sleep(3000);
        new PacketEjectMass().write();
        new PacketSplit().write();
        new PacketMove(1,1).write();
        //Thread.sleep(3000);
        thread.interrupt();
    }
}
