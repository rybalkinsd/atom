package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.Objects;

/**
 * Template class for
 */
public class Bar extends Point implements Collider  {
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

    public void setBar(Point l,Point r) {
        this.l = l;
        this.r = r;
    }
    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Bar bar = (Bar) o;
        int param = this.getR().getY();
        if (Math.abs(this.getR().getX() - this.getL().getX()) == Math.abs(bar.getR().getX() - bar.getL().getX())
                && Math.abs(param - this.getL().getY()) == Math.abs(bar.getR().getY() - bar.getL().getY())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) {
            return true;
        } else if (other.getClass().toString().equals("class ru.atom.geometry.Bar")) {
            Bar bar = (Bar) other;
            if (this.getR().getY() < bar.getL().getY()
                    || this.getR().getX() < bar.getL().getX()
                    || this.getL().getX() > bar.getR().getX()
                    || this.getL().getY() > bar.getR().getY()) {
                return false;
            }
            return true;
        } else if (other.getClass().toString().equals("class ru.atom.geometry.Point")) {
            Point bar = (Point) other;
            if (this.getR().getY() >= bar.getY()
                && this.getR().getX() >= bar.getX()
                && this.getL().getY() <= bar.getY()
                && this.getL().getX() <= bar.getX()) {
                return true;
            }

            return false;
        }

        return false;
    }
}