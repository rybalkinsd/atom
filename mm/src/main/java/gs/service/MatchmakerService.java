package gs.service;

import gs.connection.ConnectionQueue;
import gs.connection.Joins;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MatchmakerService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MatchmakerService.class);

    public long join(@NotNull String name) {
        if (Joins.getInstance().containsKey(name)) {
            return Joins.getInstance().get(name);
        } else {
            ConnectionQueue.getInstance().offer(name);
            while (!Joins.getInstance().containsKey(name)) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("Interrupted");
                }
            }
            return Joins.getInstance().get(name);
        }

    }
}
