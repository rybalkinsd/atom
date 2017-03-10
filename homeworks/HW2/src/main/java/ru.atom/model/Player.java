package ru.atom.model;

import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Ксения on 07.03.2017.
 */
public class Player implements Movable {

    private int id;
    private Point position;
    private int numOfBombs = 0;
    private long elapsedTime = 0;
    private int speed = 1;
    private int maxNumOfBombs = 1;
    private int powerOfBomb = 1;

    public Player(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getNumOfBombs() {
        return numOfBombs;
    }

    public int getMaxNumOfBombs() {
        return maxNumOfBombs;
    }

    public int getPowerOfBomb() {
        return powerOfBomb;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case DOWN: {
                return new Point(position.getX(),position.getY() - speed);
            }
            case UP: {
                return new Point(position.getX(),position.getY() + speed);
            }
            case LEFT: {
                return new Point(position.getX() - speed, position.getY());
            }
            case RIGHT: {
                return new Point(position.getX() + speed, position.getY());
            }
            case IDLE: {
                return position;
            }
            default:
        }
        throw new NotImplementedException();
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    public Player pickBonus(Bonus bonus) {
        switch (bonus.getType()) {
            case BOMB: {
                maxNumOfBombs++;
                break;
            }
            case POWER: {
                powerOfBomb++;
                break;
            }
            case SPEED: {
                speed++;
                break;
            }
            default:
        }
        return this;

    }

}
