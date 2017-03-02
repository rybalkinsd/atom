package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.lang.Math.*;
import java.util.Objects;

/**
 * Template class for
 */
public class Bar extends Point implements Collider /* super class and interfaces here if necessary */ {
    // fields
    // and methods
    private Point l;
    private Point r;


    public Point getL() {
        return this.l;
    }

    public Point getR() {
        return this.r;
    }

    public void setL(Point l) {
        this.l = l;
    }

    public void setR(Point r) {
        this.r = r;
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //if (o == null || getClass() != o.getClass()) return false;
        // cast from Object to Point
        Bar b = (Bar) o;
        if (Math.abs(this.getR().getX() - this.getL().getX()) == Math.abs(b.getR().getX() - b.getL().getX()) &&
                Math.abs(this.getR().getY() - this.getL().getY()) == Math.abs(b.getR().getY() - b.getL().getY()))
            return true;
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if(this == other) return true;
        System.out.println(other.getClass().toString());
        if (other.getClass().toString().equals("class ru.atom.geometry.Bar")) {
            Bar b = (Bar) other;
            if (this.getR().getY() < b.getL().getY() || this.getR().getX() < b.getL().getX() ||
                    this.getL().getX() > b.getR().getX() || this.getL().getY() > b.getR().getY())
                return false;
            return true;
        } else if (other.getClass().toString().equals("class ru.atom.geometry.Point")) {
            Point b = (Point) other;
            if (this.getR().getY() >= b.getY() && this.getR().getX() >= b.getX() &&
                    this.getL().getY() <= b.getY() && this.getL().getX() <= b.getX()) return true;
            return false;
        }
        return false;
    }
}