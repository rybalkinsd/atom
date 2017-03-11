package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Positionable;
import ru.atom.model.Temporary;

/**
 * Created by dmitriy on 05.03.17.
 */
public class Bomb implements GameObject, Positionable, Temporary {

    private Point position;
    private final long lifetime = 3000;
    private long workTime = 0;
    private boolean isDead = false;
    private final int id;

    public Bomb(int x, int y) {
        this.position = new Point(x, y);
        id = GameSession.nextValue();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {
        workTime += elapsed;
        if (workTime >= lifetime) {
            destroy();
            isDead = true;
        }
    }

    @Override
    public long getLifetimeMillis() {
        return lifetime;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    private void destroy() {
        //TODO destroy nearest objects
    }

}
