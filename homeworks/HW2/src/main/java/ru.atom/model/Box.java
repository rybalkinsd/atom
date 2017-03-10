package ru.atom.model;

import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Ксения on 07.03.2017.
 */
public class Box implements Positionable {

    private int id;
    private Point position;
    private boolean isBombed = false; // до столкновения с Explosion

    public Box(int id, Point position) {
        this.id = id;
        this.position = position;
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
