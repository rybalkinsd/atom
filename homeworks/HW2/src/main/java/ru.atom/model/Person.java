package ru.atom.model;

import ru.atom.geometry.Point;

public abstract class Person implements Movable {

    private Point pointOfPerson;
    private int id;
    private int speed;

    public Person() {
        pointOfPerson = new Point(0,0);
        speed = 5;
        id = 1;
    }

    @Override
    public Point move(Direction direction, long time) {
        switch (direction) {
            case UP:
                pointOfPerson = new Point(pointOfPerson.getX(), pointOfPerson.getY() + (int)(time * speed));
                break;
            case DOWN:
                pointOfPerson = new Point(pointOfPerson.getX(), pointOfPerson.getY() - (int)(time * speed));
                break;
            case LEFT:
                pointOfPerson = new Point(pointOfPerson.getX() - (int)(time * speed), pointOfPerson.getY());
                break;
            case RIGHT:
                pointOfPerson = new Point(pointOfPerson.getX() + (int)(time * speed), pointOfPerson.getY());
                break;
            case IDLE:
                break;
            default:
                break;
        }
        return pointOfPerson;
    }

    @Override
    public Point getPosition() {
        return pointOfPerson;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {

    }
}
