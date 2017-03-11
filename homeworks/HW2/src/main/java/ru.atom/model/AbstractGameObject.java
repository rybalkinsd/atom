package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Vlad on 11.03.2017.
 */
public abstract class AbstractGameObject implements Positionable, GameObject{
    protected Point position;
    protected final int id;

    public AbstractGameObject(int x, int y) {
        position = new Point(x, y);
        id = GameSession.getGameObjectId();
    }
    @Override
    public int getId(){
        return id;
    }

    @Override
    public Point getPosition(){
        return position;
    }

}
