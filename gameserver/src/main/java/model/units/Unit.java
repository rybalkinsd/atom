package model.units;

/*
  Created by Rail on 09.10.2016.
 */
/**
 * Base class for all units in {@link model.GameField}
 *
 * @author Rail Mazgutov
 */
public class Unit {
    protected int size;
    protected int x;
    protected int y;


    Unit() {
        x = 0;
        y = 0;
        size = 0;
    }

    public Unit(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }
    /**
     * Calculate distance to another unit
     * @param unit another unit
     * @return distance to second unit
     */
    public int distanceTo(Unit unit){
        int distance = (int) Math.sqrt((double)((x - unit.x)*(x - unit.x) + (y - unit.y)*(y - unit.y)));
        return distance;
    }

    public int getSize(){
        return size;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "x='" + x + '\'' +
                "y='" + y + '\'' +
                "size='" + size + '\'' +
                '}';
    }
}
