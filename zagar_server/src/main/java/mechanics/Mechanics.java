package mechanics;

import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.LeaderboardMsg;
import messageSystem.messages.ReplicateMsg;
import model.EjectedMass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Tickable;
import ticker.Ticker;

import java.time.Duration;

/**
 * Created by apomosov on 14.05.16.
 * <p>
 * Game mechanics
 */
public class Mechanics extends Service implements Tickable {
    @NotNull
    private final static Logger log = LogManager.getLogger(Mechanics.class);
    @NotNull
    private final Ticker ticker = new Ticker(this);

    public Mechanics() {
        super("mechanics");
    }

    @Override
    public void run() {
        log.info(getAddress() + " started");
        ticker.loop();
    }

    @Override
    public void tick(@NotNull Duration elapsed) {
        MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);

        //execute all messages from queue
        messageSystem.execForService(this);

        //execute game session ticks
        MatchMaker matchMaker = ApplicationContext.instance().get(MatchMaker.class);
        matchMaker.getActiveGameSessions().forEach(gameSession -> {
            gameSession.tickRemoveAfk();
            gameSession.tickGenerators(elapsed);
            gameSession.getField()
                    .getCells(EjectedMass.class)
                    .forEach(em->em.tickMove(gameSession.getField().getRegion(),elapsed));
        });


        log.trace("Start replication");
        Message message = new ReplicateMsg(getAddress());
        Message lbMessage = new LeaderboardMsg(getAddress());
        messageSystem.sendMessage(message);
        messageSystem.sendMessage(lbMessage);

    /*System.out.println("Conns " +
            ApplicationContext.instance().get(ClientConnections.class).getConnections());*/

        log.trace("Mechanics tick() finished");
    }

}
