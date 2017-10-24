package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Tickable, Positionable {
    private static final Logger log = LogManager.getLogger(Bomb.class); //нужен для отчетности
    private final int bombId;
    public int rangeExp;
    private static final long TIME_LIFE = 10;
    private final Point position;
    private long timeLife;

    public Bomb(final Point position) {
        this.bombId = GameSession.getBombId();
        this.position = position;
        this.rangeExp = 1;
        log.info("New bomb was created: id={}, position = ({},{})", bombId, position.getX(), position.getY());
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return bombId;
    }

    @Override
    public void tick(long elapsed) {
        timeLife += elapsed;

    }
}
