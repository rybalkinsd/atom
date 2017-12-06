package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Fire extends GameObject implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Fire.class);
    private static final int LIFETIME = 200;
    private static final int FIRE_WIDTH = 38;
    private static final int FIRE_HEIGHT = 38;

    private int elapsed = 0;

    public Fire(GameSession session, Point position) {
        super(session, position, FIRE_WIDTH, FIRE_HEIGHT);
        logger.info("New Fire id={}, position={}, session_ID={}", id, position, session.getId());
    }

    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
        if (this.elapsed >= LIFETIME) {
            session.removeById(this.id);
        }
    }
}
