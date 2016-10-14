package model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Orlov on 11.10.2016.
 */

public class Coordinate {
    @NotNull
    private double Xcoordinate;
    @NotNull
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

