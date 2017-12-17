package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Bonus extends GameObject implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Bomb.class);
    private static final int LIFETIME = 3000;
    private static final int BONUS_WIDTH = 28;
    private static final int BONUS_HEIGHT = 28;
    private transient int elapsed = 0;
    private String bonusType;

    public Bonus(GameSession session, Point position, String type) {
        super(session, new Point(position.getX(), position.getY()),
                type, BONUS_WIDTH, BONUS_HEIGHT);
        this.bonusType = type;
        logger.info("New Bonus id={}, position={}, type = {}, session_ID = {}", id, position, type, session.getId());
    }


    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
    }

    public boolean dead() {
        return this.elapsed >= LIFETIME;
    }
}
