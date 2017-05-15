package ru.atom.bombergirl.gamemodel.model;

import ru.atom.bombergirl.gamemodel.geometry.Collider;
import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.mmserver.GameSession;

/**
 * Created by dmitriy on 05.03.17.
 */
public class Wood implements Block, GameObject, Positionable, Temporary {

    private Point position;
    private final int id;
    private boolean isDead = false;

    public Wood(int x, int y) {
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
    public boolean isColliding(Collider other) {
        return false; //DUMMY
    }

    @Override
    public void destroy() {
        isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public long getLifetimeMillis() {
        return 0; //dummy, we don't need to record Wood's lifetime
    }

    @Override
    public void tick(long elapsed) {
        return; //dummy, Wood doesn't tick
    }
}
