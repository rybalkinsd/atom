package model;

/**
 * Created by valentin on 10.10.16.
 */
public class Coordinate {
    private Double positionX;
    private Double positionY;
    public Coordinate(Double x, Double y){this.positionX = x; this.positionY = y;};
    public Double getPositionX(){return this.positionX;};
    public Double getPositionY(){return this.positionY;};
    @Override
    public String toString() {
        return "(" + this.positionX + "," + this.positionY + ")";
    }
}
