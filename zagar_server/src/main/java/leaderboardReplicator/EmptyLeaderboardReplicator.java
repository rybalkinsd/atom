package leaderboardReplicator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import replication.EmptyReplicator;

/**
 * Created by User on 28.11.2016.
 */
public class EmptyLeaderboardReplicator implements LeaderboardReplicator {
    private static final Logger log = LogManager.getLogger(EmptyLeaderboardReplicator.class);
    @Override
    public void replicate() {
        log.info("Empty leaderboard replication");
    }
}
