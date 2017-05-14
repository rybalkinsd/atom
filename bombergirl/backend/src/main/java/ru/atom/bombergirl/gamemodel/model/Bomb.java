package ru.atom.bombergirl.gamemodel.model;

import ru.atom.bombergirl.gamemodel.geometry.Collider;
import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.mmserver.GameSession;

/**
 * Created by dmitriy on 05.03.17.
 */
public class Bomb implements GameObject, Positionable, Temporary, Tickable, Collider {

    private Point position;
    private final long lifetime = 3000;
    private long workTime = 0;
    private boolean isDead = false;
    private final int id;
    private GameSession session;

    /*public Bomb(int x, int y) {
        this.position = new Point(x, y);
        id = GameSession.nextValue();
    }*/

    private Bomb(GameSession session) {
        this.session = session;
        id = GameSession.nextValue();
    }

    public static void create(Point position, GameSession session) {
        Bomb thisBomb = new Bomb(session);
        thisBomb.setPosition(position);
        session.addGameObject(thisBomb);
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

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public boolean isColliding(Collider other) {
        return false; //DUMMY
    }

}
