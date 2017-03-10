package ru.atom.model.items;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.GameSession;
import ru.atom.model.Positionable;
import ru.atom.model.Temporary;

/**
 * Created by IGIntellectual on 10.03.2017.
 */
public class Bomb implements Temporary, Positionable {
    private static final Logger LOG = LogManager.getLogger(Bomb.class);
    private static final int LIFE_TIME_MILLIS = 1500;

    private Point position;
    private int lifeTime;
    private int power;
    private int id;

    public Bomb(int x, int y, int power) {
        this.position = new Point(x, y);
        this.lifeTime = 0;
        this.id = GameSession.getId();
        this.power = power;
        LOG.info("Bomb ( id = {} ) was created in ( {};{} ), lifeTime == {}, power == {} ", this.id,
                position.getX(), position.getY(), this.getLifetimeMillis(), this.power);
    }

    @Override
    public long getLifetimeMillis() {
        return this.LIFE_TIME_MILLIS;
    }

    @Override
    public boolean isDead() {
        return this.lifeTime >= this.LIFE_TIME_MILLIS;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void tick(long elapsed) {
        lifeTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

}
