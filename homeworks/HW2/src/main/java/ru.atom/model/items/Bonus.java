package ru.atom.model.items;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Positionable;
import ru.atom.model.Temporary;


/**
 * Created by IGIntellectual on 10.03.2017.
 */
public class Bonus implements Temporary, Positionable {
    private static final Logger LOG = LogManager.getLogger(Bonus.class);
    private static final int LIFE_TIME_MILLIS = 3500;

    private int lifeTime;
    private Type bonusType;

    private int id;

    private Point position;

    public enum Type {
        BOMB,
        SPEED,
        RANGE,
    }

    public Bonus(int x, int y, Type bonusType)
    {
        this.position = new Point(x, y);
        this.id = GameSession.getId();
        this.lifeTime = 0;
        this.bonusType = bonusType;
        LOG.info("BONUS (id = {}) was created in ({};{}) lifeTime == {}, BONUS == {} ", this.id, position.getX(), position.getY(), this.getLifetimeMillis(), this.bonusType);
    }

    @Override
    public long getLifetimeMillis(){
        return this.LIFE_TIME_MILLIS;
    }

    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public void tick(long elapsed){
        lifeTime += elapsed;
    }

    @Override
    public boolean isDead(){
        return this.lifeTime >= this.LIFE_TIME_MILLIS;
    }

    @Override
    public Point getPosition(){
        return this.position;
    }

}
