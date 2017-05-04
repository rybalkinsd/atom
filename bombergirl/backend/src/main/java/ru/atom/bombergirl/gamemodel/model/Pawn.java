package ru.atom.bombergirl.gamemodel.model;

import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.mmserver.GameSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 05.03.17.
 */
public class Pawn implements GameObject, Positionable, Movable, Tickable {

    private Point position;
    private int step = 1;
    private Direction direction = Direction.IDLE;
    private final int id;
    private boolean toPlantBomb = false;
    private List<Action> actions = new ArrayList<>();

    public Pawn(Point p) {
        this.position = new Point(p.getX(), p.getY());
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

    public void plantBomb() {
        if (!toPlantBomb) {
            return;
        }
        Bomb.create(this.position);
        toPlantBomb = false;
    }

    public void makePlantBomb() {
        toPlantBomb = true;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case DOWN:
                position = new Point(position.getX(), position.getY() - step);
                break;
            case UP:
                position = new Point(position.getX(), position.getY() + step);
                break;
            case LEFT:
                position = new Point(position.getX() - step, position.getY());
                break;
            case RIGHT:
                position = new Point(position.getX() + step, position.getY());
                break;
            default:
                break;
        }
        return position;
    }

    @Override
    public void tick(long elapsed) {
        for (Action a : actions) {
            a.act(this);
        }
        plantBomb();
        actions.clear();
    }

    public void addAction(Action action) {
        actions.add(action);
    }
}
