package ru.atom.model;

import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Ксения on 07.03.2017.
 */
public class Bonus implements Positionable {
    public enum TypesOfBonus {
        SPEED, BOMB, POWER
    }

    private int id;
    private Point position;
    private TypesOfBonus type;
    boolean isPicked = false; //до столкновения с Player

    public Bonus(int id, Point position, TypesOfBonus type) {
        this.id = id;
        this.position = position;
        this.type = type;
    }

    public TypesOfBonus getType() {
        return type;
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
