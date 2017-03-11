package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Antonio on 11.03.2017.
 */
public class BonusBox implements Positionable {
    private Point position;
    private final int id;
    private boolean isDead = false;

    public enum TypesOfBonus {

        VELOCITY, BOMB, POWER
    }

    private TypesOfBonus bonus;

    public BonusBox(int x, int y, TypesOfBonus bonus) {
        position = new Point(x, y);
        id = GameSession.idCounter();
        this.bonus = bonus;
    }

    public TypesOfBonus getType() {
        return bonus;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public Point getPosition() {
        return position;
    }

}
