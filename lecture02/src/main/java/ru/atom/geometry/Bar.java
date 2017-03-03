package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by kinetik on 01.03.17.
 */
public class Bar implements Collider {
    private int firstCornerX;
    private int secondCornerX;
    private int firstCornerY;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int getFirstCornerX() {
        return this.firstCornerX;
    }

    public int getFirstCornerY() {
        return this.firstCornerY;
    }

    public int getSecondCornerX() {
        return this.secondCornerX;
    }

    public int getSecondCornerY() {
        return this.secondCornerY;
    }

    public void setFirstCornerX(int firstCornerX) {
        this.firstCornerX = firstCornerX;
    }

    public void setFirstCornerY(int firstCornerY) {
        this.firstCornerY = firstCornerY;
    }

    public void setSecondCornerX(int secondCornerX) {
        this.secondCornerX = secondCornerX;
    }

    public void setSecondCornerY(int secondCornerY) {
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;
        if (bar.getFirstCornerX() == this.getFirstCornerX() && bar.getFirstCornerY() ==  this.getFirstCornerY()
               && bar.getSecondCornerX() == this.getSecondCornerX()
                && bar.getSecondCornerY() == this.getSecondCornerY()) {
            return true;
        } else if (bar.getFirstCornerX() == this.getSecondCornerX() && bar.getFirstCornerY() == this.getSecondCornerY()
                && bar.getSecondCornerX() == this.getFirstCornerX()
                && bar.getSecondCornerY() == this.getFirstCornerY()) {
            return true;
        } else if (bar.getFirstCornerX() == this.getSecondCornerX() && bar.getFirstCornerY() == this.getFirstCornerY()
                && bar.getSecondCornerX() == this.getFirstCornerX()
                && bar.getSecondCornerY() == this.getSecondCornerY()) {
            return true;
        } else if (bar.getFirstCornerX() == this.getFirstCornerX() && bar.getFirstCornerY() == this.getSecondCornerY()
                && bar.getSecondCornerX() == this.getSecondCornerX()
                && bar.getSecondCornerY() == this.getFirstCornerY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hashValue = this.getFirstCornerX();
        hashValue = 13 * hashValue + this.getSecondCornerX();
        hashValue = 13 * hashValue + this.getFirstCornerY();
        hashValue = 13 * hashValue + this.getSecondCornerY();
        return hashValue;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other instanceof Point) {
            Point point = (Point) other;
            if (point.getxCoord() >= this.getFirstCornerX() && point.getxCoord() <= this.getSecondCornerX()
                   && point.getyCoord() >= this.getFirstCornerY() && point.getyCoord() <= this.getSecondCornerY()) {
                return true;
            } else {
                return false;
            }
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (bar.equals(this)) {
                return true;
            } else {
                if (bar.getFirstCornerX() > this.getSecondCornerX() || bar.getSecondCornerY() < this.getFirstCornerY()
                        || bar.getSecondCornerX() < this.getFirstCornerX()
                        || bar.getFirstCornerY() > this.getSecondCornerY()) {
                    return false;
                } else if (this.getFirstCornerX() > bar.getSecondCornerX()
                        || this.getSecondCornerY() < bar.getFirstCornerY()
                        || this.getSecondCornerX() < bar.getFirstCornerX()
                        || this.getFirstCornerY() > this.getSecondCornerY()) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            throw new NotImplementedException();
        }
    }
}
