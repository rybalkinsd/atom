package replication;

import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by User on 28.11.2016.
 */
public class EmptyReplicator implements Replicator {
    private static final Logger log = LogManager.getLogger(EmptyReplicator.class);

    @Override
    public void replicate() {
        log.info("Empty replication");
    }
}
