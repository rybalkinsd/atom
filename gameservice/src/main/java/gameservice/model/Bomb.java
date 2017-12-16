package gameservice.model;

import gameservice.geometry.Point;
import org.slf4j.LoggerFactory;

public class Bomb extends GameObject implements Tickable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Bomb.class);

    private static final int LIFETIME = 4000;
    private static final int BOMB_WIDTH = 28;
    private static final int BOMB_HEIGHT = 28;
    private transient int timer = 0;

    public Bomb(GameSession session, Point position, Pawn owner) {
        super(session, new Point(position.getX(), position.getY()),
                "Bomb", BOMB_WIDTH, BOMB_HEIGHT);
        log.info("New Bomb id={}, position={}, session_ID = {}", id, position, session.getId());
    }

    @Override
    public void tick(int elapsed) {
        this.timer += elapsed;
    }

    public boolean dead() {
        return this.timer >= LIFETIME;
    }
}
