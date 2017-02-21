package model;

/**
 * Created by Вьюнников Виктор on 11.10.2016.
 */
public class Coordinate {
    private double Xcoordinate;
    private double Ycoordinate;

    public double getXcoordinate(){return Xcoordinate;}
    public double getYcoordinate(){return Ycoordinate;}

    public void setXcoordinate(double xcoordinate) {Xcoordinate = xcoordinate;}
    public void setYcoordinate(double ycoordinate) {Ycoordinate = ycoordinate;}

    @Override
    public String toString(){
        return
                "Coordinate: (" + Xcoordinate+","+Ycoordinate+")";
    }

}
