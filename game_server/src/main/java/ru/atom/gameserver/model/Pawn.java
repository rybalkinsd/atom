package ru.atom.gameserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.atom.gameserver.geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr on 06.12.2017.
 */
public class Pawn extends AbstractGameObject implements Movable {

    private float velocity;
    private int maxBombs;
    private int bombPower;
    private float speedModifier;
    @JsonIgnore
    private List<Bomb> bombs;

    public Pawn(int id, Point position, float velocity, int maxBombs) {
        super(id, position);
        this.velocity = velocity;
        this.maxBombs = maxBombs;
        this.bombPower = 1;
        this.speedModifier = 1.0f;
        bombs = new ArrayList<>();
    }

    public float getVelocity() {
        return velocity;
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public int getBombPower() {
        return bombPower;
    }

    public float getSpeedModifier() {
        return speedModifier;
    }

    @Override
    public void tick(long elapsed) {

    }

    @Override
    public Point move(Direction direction, long time) {
        return null;
    }
}
