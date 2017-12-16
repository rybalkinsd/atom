package gameservice.model;

import gameservice.geometry.Point;
import org.slf4j.LoggerFactory;

public class Fire extends GameObject implements Tickable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Fire.class);

    private static final int LIFETIME = 200;
    private static final int FIRE_WIDTH = 38;
    private static final int FIRE_HEIGHT = 38;
    private transient int timer = 0;

    public Fire(GameSession session, Point position) {
        super(session, new Point(position.getX(), position.getY()),
                "Fire", FIRE_WIDTH, FIRE_HEIGHT);
        log.info("New Fire id={}, position={}, session_ID={}", id, position, session.getId());
    }

    @Override
    public void tick(int elapsed) {
        this.timer += elapsed;
    }

    public boolean dead() {
        return this.timer >= LIFETIME;
    }
}
