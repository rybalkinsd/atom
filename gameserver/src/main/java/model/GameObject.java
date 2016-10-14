package model;

import java.awt.*;


/**
 * Created by Вьюнников Виктор on 10.10.2016.
 */

//Родительский класс всех объектов на игровом поле
public abstract class GameObject {
    private Coordinate coordinate;

    private double Radius;
    //private Color color;


    public Coordinate getCoordinate(){return coordinate;}
    public double getRadius() {return Radius;}
    //public Color getColor(){return color;}

    public void setCoordinate(Coordinate coordinate) {this.coordinate = coordinate;}
    //public void setColor(Color color) {this.color = color;}
    public void setRadius(double radius) {Radius = radius;}

    public GameObject() {
        coordinate = new Coordinate();
    }
    @Override
    public String toString() {
        return "GameObject::" +
                "Radius: " + getRadius() + " " +
                getCoordinate().toString();
    }

}
