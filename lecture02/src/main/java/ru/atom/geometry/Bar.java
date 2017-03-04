package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */
public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int temp;
        if (firstCornerX > secondCornerX) {
            temp = firstCornerX;
            firstCornerX = secondCornerX;
            secondCornerX = temp;
        }
        if (firstCornerY > secondCornerY) {
            temp = firstCornerY;
            firstCornerY = secondCornerY;
            secondCornerY = temp;
        }
        this.firstCornerX = firstCornerX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    private boolean pointIntervalIntersection(int pointZ, int z1, int z2) {
        int temp;
        if (z1 > z2) {
            temp = z1;
            z1 = z2;
            z2 = temp;
        }
        if ((pointZ >= z1) && (pointZ <= z2)) {
            return true;
        }
        return false;
    }

    private boolean intervalIntervalIntersection(int firstCornerZ1, int secondCornerZ1,
                                                 int firstCornerZ2, int secondCornerZ2) {
        int temp;
        if (firstCornerZ1 > firstCornerZ2) {
            temp = firstCornerZ1;
            firstCornerZ1 = firstCornerZ2;
            firstCornerZ2 = temp;
            temp = secondCornerZ1;
            secondCornerZ1 = secondCornerZ2;
            secondCornerZ2 = temp;
        }
        if (secondCornerZ1 >= firstCornerZ2) {
            return true;
        }
        return false;
    }

    public boolean isColliding(Collider other) {
        if (this.equals(other)) {
            return true;
        }
        if (other instanceof Point) {
            Point point = (Point) other;
            if (pointIntervalIntersection(point.getX(), this.firstCornerX, this.secondCornerX)) {
                if (pointIntervalIntersection(point.getY(), this.firstCornerY, this.secondCornerY)) {
                    return true;
                }
            }
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (intervalIntervalIntersection(bar.firstCornerX, bar.secondCornerX,
                                            this.firstCornerX, this.secondCornerX)) {
                if (intervalIntervalIntersection(bar.firstCornerY, bar.secondCornerY,
                                                this.firstCornerY, this.secondCornerY)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if ((this.firstCornerX == bar.firstCornerX) && (this.firstCornerY == bar.firstCornerY)) {
            if ((this.secondCornerX == bar.secondCornerX) && (this.secondCornerY == bar.secondCornerY)) {
                return true;
            }
        }
        return false;
    }
}