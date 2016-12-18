package model;

/**
 * Created by User on 10.10.2016.
 */
public class Location {
    private double x;
    private double y;

    public Location(double x, double y){
        setLocation(x,y);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
    
    public void setLocation(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x + " " +
                "y=" + y +
                '}';
    }


}
