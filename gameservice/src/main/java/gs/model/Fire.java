package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Fire extends GameObject implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Fire.class);
    private static final int LIFETIME = 200;
    private static final int FIRE_WIDTH = 38;
    private static final int FIRE_HEIGHT = 38;

    private transient int elapsed = 0;

    public Fire(GameSession session, Point position) {
        super(session, new Point(position.getX(), position.getY()),
                "Fire", FIRE_WIDTH, FIRE_HEIGHT);
    }

    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
    }

    public boolean dead() {
        return this.elapsed >= LIFETIME;
    }
}
