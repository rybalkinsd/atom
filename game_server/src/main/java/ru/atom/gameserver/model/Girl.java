package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr on 06.12.2017.
 */
public class Girl extends AbstractGameObject implements Movable {

    private final int velocity;
    private int maxBombs;
    private List<Bomb> bombs;

    public Girl(int id, Point position, int velocity, int maxBombs) {
        super(id, position);
        this.velocity = velocity;
        this.maxBombs = maxBombs;
        bombs = new ArrayList<>();
    }

    public float getVelocity() {
        return velocity;
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public int getBombPower() {
        return 1;
    }

    public float getSpeedModifier() {
        return 1.0f;
    }

    @Override
    public void tick(long elapsed) {

    }

    @Override
    public Point move(Direction direction, long time) {
        return null;
    }
}
