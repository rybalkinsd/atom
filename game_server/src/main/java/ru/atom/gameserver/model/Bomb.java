package ru.atom.gameserver.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.tick.Tickable;

/**
 * Created by Alexandr on 06.12.2017.
 */
public class Bomb extends AbstractGameObject implements Tickable {

    @JsonProperty("lifetime")
    private long lifetime;
    @JsonProperty("power")
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
    public void tick(long elapsed) {

    }

}
