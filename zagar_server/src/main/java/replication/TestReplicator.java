package replication;

import main.ApplicationContext;
import model.Player;
import network.ClientConnections;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import utils.JSONHelper;
import java.io.FileInputStream;
import java.util.Map;

public class TestReplicator implements Replicator {
    @Override
    public void replicate(){
        for (Map.Entry<Player, Session> connection : ApplicationContext.instance()
                .get(ClientConnections.class).getConnections()) {
            try {
                FileInputStream in = new FileInputStream("src/main/resources/replicator.json");
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                JSONHelper.fromJSON(new String(bytes), PacketReplicate.class)
                        .write(connection.getValue());
            } catch (Exception e) {}
        }
    }
}