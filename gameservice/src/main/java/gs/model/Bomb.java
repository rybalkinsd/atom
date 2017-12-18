package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Bomb extends GameObject implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Bomb.class);
    private static final int LIFETIME = 3000;
    private static final int BOMB_WIDTH = 28;
    private static final int BOMB_HEIGHT = 28;
    private transient Girl owner;
    private transient int elapsed = 0;

    public Bomb(GameSession session, Point position, Girl owner) {
        super(session, new Point(position.getX(), position.getY()),
                "Bomb", BOMB_WIDTH, BOMB_HEIGHT);
        this.owner = owner;
    }

    public Girl getOwner() {
        return this.owner;
    }

    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
    }

    public boolean dead() {
        return this.elapsed >= LIFETIME;
    }
}
