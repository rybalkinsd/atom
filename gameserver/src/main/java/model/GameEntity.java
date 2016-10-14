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

    /**
     * Construct game entity with params and calculate its radius based on its mass
     *
     * @param coordinates
     * @param color
     * @param mass
     */
    GameEntity(Point2D coordinates, Color color, int mass) {
        this.coordinates = coordinates;
        this.color = color;
        this.mass = mass;
        this.radius = calculateRadius(this.mass);
    }

    //Getters
    Point2D getCoordinates(){return coordinates;}
    Color getColor(){return  color;}
    double getRadius(){return radius;}
    int getMass(){return  mass;}

    /**
     * Set new mass of entity and recalculate entity's radius
     * @param mass - new mass
     */
    void setMass(int mass){
        this.mass = mass;
        this.radius = calculateRadius(this.mass);
    }

    /**
     * Calculate entity's radius base on its mass
     * @param mass - entity's mass
     * @return radius
     */
    private static double calculateRadius(int mass){ return sqrt(mass / Math.PI); }

    @Override
    public String toString(){
        return "{ coordinates = " + coordinates +
                " color = " + color + " mass = "+ mass + " radius = " + radius;
    }

}
