package ru.atom.model;

import ru.atom.geometry.Point;


/**
 * Created by Antonio on 11.03.2017.
 */
public class Girl implements Movable, Positionable {
    private Point position;
    private final int id;
    private long elapsedTime = 0;
    private int NumberOfBombs = 1;
    private int NumberofMortgagedbomb=0;
    private int BombForce = 1;
    private int velocity = 20;
    private boolean isDead = false;

    public Girl(int x, int y){
        position=new Point(x,y);
        id=GameSession.idCounter();

    }
    public void IncreaseVelocity(){
        velocity+=15;
    }
    public void IncreaseNumberOfBombs(){
        NumberOfBombs+=1;
    }
    public void IncreaseNumberofMortgagedbomb(){
        NumberofMortgagedbomb+=1;

    }
    public void DecreaseNumberofMortgagedbomb(){
        if (NumberofMortgagedbomb>1) {
            NumberofMortgagedbomb -= 1;
        }
    }
    public void IncreaseBombForce(){
        BombForce+=2;
    }


    @Override

    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime+=elapsed;

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
