package ru.atom.bombergirl.gamemodel.model;

import ru.atom.bombergirl.gamemodel.geometry.Collider;
import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.mmserver.GameSession;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dmitriy on 05.03.17.
 */
public class Pawn implements GameObject, Positionable, Movable, Tickable, Collider {

    private Point position;
    private int step = 1;
    private Direction direction = Direction.IDLE;
    private final int id;
    private boolean toPlantBomb = false;
    private List<Action> actions = new ArrayList<>();
    private GameSession session;

    public Pawn(Point p, GameSession s) {
        this.position = new Point(p.getX(), p.getY());
        session = s;
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
        List<GameObject> objects = session
                .getGameObjects()
                .stream()
                .filter(o -> o instanceof Collider)
                .collect(Collectors.toList());
        Point preChangePosition = position;
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
        for (GameObject o : objects) {
            if (this.isColliding((Collider) o)) {
                return preChangePosition;
            }
        }
        return position;
    }

    public boolean isColliding(Collider c) {
        if (this.getPosition().getX() - GameField.GRID_SIZE / 2 <= c.getPosition().getY()*32 - GameField.GRID_SIZE / 2
                && this.getPosition().getX() + GameField.GRID_SIZE / 2 <= c.getPosition().getY()*32 + GameField.GRID_SIZE / 2
                && this.getPosition().getY() - GameField.GRID_SIZE / 2 >= c.getPosition().getX()*32 - GameField.GRID_SIZE / 2
                && this.getPosition().getY() + GameField.GRID_SIZE / 2 >= c.getPosition().getX()*32 + GameField.GRID_SIZE / 2
                ||  c.getPosition().getX() - GameField.GRID_SIZE / 2 <= this.getPosition().getY()*32 - GameField.GRID_SIZE / 2
                && c.getPosition().getX() + GameField.GRID_SIZE / 2 <= this.getPosition().getY()*32 + GameField.GRID_SIZE / 2
                && c.getPosition().getY() - GameField.GRID_SIZE / 2 >= this.getPosition().getX()*32 - GameField.GRID_SIZE / 2
                && c.getPosition().getY() + GameField.GRID_SIZE / 2 >= this.getPosition().getX()*32 + GameField.GRID_SIZE / 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tick(long elapsed) {
//        Action a;
        Iterator<Action> it = actions.iterator();
        /*Map<Direction, Boolean> isDirectionDone = new HashMap<>();
        for (Direction d: Movable.Direction.values()) {
            isDirectionDone.put(d, false);
        }*/
        while (it.hasNext()) {
            it.next().act(this);
        }
        plantBomb();
        actions.clear();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
