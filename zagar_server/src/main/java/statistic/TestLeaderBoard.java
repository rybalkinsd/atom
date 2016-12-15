package statistic;

import main.ApplicationContext;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import org.eclipse.jetty.websocket.api.Session;
import utils.JSONHelper;
import java.io.FileInputStream;
import java.util.Map;

public class TestLeaderBoard implements LeaderBoard {

    @Override
    public void getLeaders(int number){
        for (Map.Entry<Player, Session> connection : ApplicationContext.instance()
                .get(ClientConnections.class).getConnections()) {
            try {
                FileInputStream in = new FileInputStream("src/main/resources/leaderboard.json");
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                JSONHelper.fromJSON(new String(bytes), PacketLeaderBoard.class)
                        .write(connection.getValue());
            } catch (Exception e) {}
        }
    }
}