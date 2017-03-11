package ru.atom.model;
import ru.atom.geometry.Point;


/**
 * Created by Antonio on 11.03.2017.
 */
public class IndestructibleStone implements Positionable{
    private Point postion;
    private final int id;
    public IndestructibleStone(int x, int y){
        postion=new Point(x,y);
        id=GameSession.idCounter();
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return postion;
    }
}
