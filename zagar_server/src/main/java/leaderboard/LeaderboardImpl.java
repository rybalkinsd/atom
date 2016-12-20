package leaderboard;

import main.ApplicationContext;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.SendLeaderboardMsg;
import messageSystem.messages.UpdateLeaderboardMsg;
import model.Cell;
import model.Player;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import ticker.Tickable;
import ticker.Ticker;

import javax.validation.constraints.NotNull;
import java.util.Map;


/**
 * Created by svuatoslav on 11/27/16.
 */
public class LeaderboardImpl extends Leaderboard implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(LeaderboardImpl.class);

    @Override
    public void run() {
        log.info(getAddress() + " started");
        Ticker ticker = new Ticker(this, 1);
        ticker.loop();
    }

    @Override
    public void tick(long elapsedNanos) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e);
            Thread.currentThread().interrupt();
        }

        //log.info("Start leaderboard replication");
        MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
        Message message = new SendLeaderboardMsg(this.getAddress());
        messageSystem.sendMessage(message);

        //log.info("Start leaderboard update");
        message = new UpdateLeaderboardMsg(this.getAddress());
        messageSystem.sendMessage(message);

        messageSystem.execForService(this);
    }

    @Override
    public void update() {
        //log.info("Updating leaderboard");
        ClientConnections clientConnections = ApplicationContext.instance().get(ClientConnections.class);
        for (Map.Entry<Player, Session> connection : clientConnections.getConnections()) {
            int score=0;
            Player player=connection.getKey();
            if(player.getCells().size()==1) {
                for (Cell cell : player.getCells()) {
                    score += cell.getMass() * cell.getMass() / 100;
                }
                LeaderboardState.update(player.getName(), score);

            }
        }
    }
}