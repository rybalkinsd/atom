package ru.atom.gameserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.atom.gameserver.geometry.Bar;
import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.tick.Tickable;

/**
 * Created by Alexandr on 06.12.2017.
 */
public class Bomb extends SaneGameObject implements Tickable, Static {

    private long lifetime;
    private final int power;

    public Bomb(int id, Point position, long lifetime, int power) {
        super(id, position);
        this.lifetime = lifetime;
        this.power = power;
    }

    public long getLifetime() {
        return lifetime;
    }

    public int getPower() {
        return power;
    }

    @Override
    public void calculateBar() {
        setBar(new Bar(getPosition(), 28, 28));
    }

    @Override
    public void tick(long elapsed) {
        lifetime -= elapsed;
        if (lifetime <= 0) {
            modelsManager.putFire(getPosition(), 300, power);
            destroy();
        }
    }

}
