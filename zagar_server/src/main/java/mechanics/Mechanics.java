package mechanics;

import accountserver.api.Authentification;
import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.ReplicateLeaderboardMsg;
import messageSystem.messages.ReplicateMsg;
import model.GameConstants;
import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Tickable;
import ticker.Ticker;

/**
 * Created by User on 28.11.2016.
 */
public class Mechanics extends Service implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(Mechanics.class);
    private int times = 1;

    public Mechanics() {
        super("mechanics");
    }

    @Override
    public void run() {
        log.info(getAddress() + " started");
        Ticker ticker = new Ticker(this, GameConstants.SERVER_FPS);
        ticker.loop();
    }

    @Override
    public void tick(long elapsedNanos) {
        log.info("Start replication");
        if(times == 64000){
            for(GameSession g:ApplicationContext.get(MatchMaker.class).getActiveGameSessions()){
                for(Player p:g.getPlayers()){
                    Authentification.LB.updateScore(p.getId(),p.getPts());
                }
            }
            times=1;
        }
        ApplicationContext.get(MatchMaker.class).tick();

        @NotNull MessageSystem messageSystem = ApplicationContext.get(MessageSystem.class);
        messageSystem.executeForService(this);

        Message message = new ReplicateMsg(this.getAddress());
        messageSystem.sendMessage(message);
        message = new ReplicateLeaderboardMsg(this.getAddress());
        messageSystem.sendMessage(message);
    }

    public boolean ejectMass(Player player){
        log.info(player + " eject mass");
        return true;
    }

    public boolean split(Player player){
        log.info(player + " split");
        return true;
    }
}
