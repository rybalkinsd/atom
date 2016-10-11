package model;


import java.awt.Color;
import java.awt.geom.Point2D;

import static java.lang.Math.sqrt;

/**
 * Created by Klissan on 09.10.2016.
 */

public abstract class GameEntity
{
    private Point2D coordinates;
    private Color color;
    private int mass;
    private double radius;

    GameEntity(Point2D coordinates, Color color, int mass) {
        this.coordinates = coordinates;
        this.color = color;
        this.mass = mass;
        this.radius = calculateRadius(this.mass);
    }

    Point2D getCoordinates(){return coordinates;}
    Color getColor(){return  color;}
    double getRadius(){return radius;}
    int getMass(){return  mass;}

    void setMass(int mass){
        this.mass = mass;
        this.radius = calculateRadius(this.mass);
    }


    private static double calculateRadius(int mass){ return sqrt(mass / Math.PI); }
}
