package ru.atom.model;

import ru.atom.geometry.Point;


/**
 * Created by Antonio on 11.03.2017.
 */
public class Girl implements Movable, Positionable {
    private Point position;
    private final int id;
    private long elapsedTime = 0;
    private int numberOfBombs = 1;
    private int numberofMortgagedbomb = 0;
    private int bombForce = 1;
    private int velocity = 20;
    private boolean isDead = false;

    public Girl(int x, int y) {
        position = new Point(x, y);
        id = GameSession.idCounter();

    }

    public void increaseVelocity() {
        velocity += 15;
    }

    public void increaseNumberOfBombs() {
        numberOfBombs += 1;
    }

    public void increaseNumberofMortgagedbomb() {
        numberofMortgagedbomb += 1;

    }

    public void decreaseNumberofMortgagedbomb() {
        if (numberofMortgagedbomb > 1) {
            numberofMortgagedbomb -= 1;
        }
    }

    public void increaseBombForce() {
        bombForce += 2;
    }


    @Override

    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;

    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + velocity);
                return position;
            case DOWN:
                position = new Point(position.getX(), position.getY() - velocity);
                return position;
            case RIGHT:
                position = new Point(position.getX() + velocity, position.getY());
                return position;
            case LEFT:
                position = new Point(position.getX() - velocity, position.getY());
                return position;
            case IDLE:
                return position;
            default:
                return position;
        }
    }
}
