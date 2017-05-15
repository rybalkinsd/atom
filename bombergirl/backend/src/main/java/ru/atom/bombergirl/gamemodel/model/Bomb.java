package ru.atom.bombergirl.gamemodel.model;

import javafx.geometry.Pos;
import ru.atom.bombergirl.gamemodel.geometry.Collider;
import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.mmserver.GameSession;

import java.util.List;

import static java.lang.Math.abs;

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
    private int pawnId;

    /*public Bomb(int x, int y) {
        this.position = new Point(x, y);
        id = GameSession.nextValue();
    }*/

    private Bomb(GameSession session, int pawnId) {
        this.session = session;
        this.pawnId = pawnId;
        id = GameSession.nextValue();
    }

    public static void create(Point position, GameSession session, int pawnId) {
        Bomb thisBomb = new Bomb(session, pawnId);
        thisBomb.setPosition(position);
        session.addGameObject(thisBomb);
    }

    public int getPawnId() {
        return pawnId;
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

    public void destroy() {
        List<GameObject> gameObjects = session.getGameObjects();
        for (GameObject o : gameObjects) {
            if (o instanceof Temporary) {
                if (!(o instanceof Bomb)) {
                    if (o instanceof Positionable) {
                        if ((abs(((Positionable) o).getPosition().getX() - this.position.getX()) <= GameField.GRID_SIZE
                                && Math.abs(((Positionable) o).getPosition().getY() - this.position.getY()) < 10)
                                || (abs(((Positionable) o).getPosition().getY() - this.position.getY()) <= GameField.GRID_SIZE
                                && Math.abs(((Positionable) o).getPosition().getX() - this.position.getX()) < 10)) {
                            ((Temporary) o).destroy();
                        }
                    }
                }
            }
            if (o.getClass() == Temporary.class &&
                    o.getClass() != Bomb.class &&
                    o.getClass() == Positionable.class) {

            }
        }
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public boolean isColliding(Collider other) {
        return false; //DUMMY
    }

}
