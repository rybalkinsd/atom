package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Template class for
 */

public class Bar implements Collider {

    private Point leftBotPoint = new Point();
    private Point rightTopPoint = new Point();

    public Bar(int firstPointX, int firstPointY, int secondPointX, int secondPointY) {
        this.leftBotPoint.setCoordinates(Math.min(firstPointX, secondPointX), Math.min(firstPointY, secondPointY));
        this.rightTopPoint.setCoordinates(Math.max(firstPointX, secondPointX), Math.max(firstPointY, secondPointY));
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // cast from Object to Bar
        Bar bar = (Bar) o;
        return this.leftBotPoint.equals(bar.leftBotPoint) && this.rightTopPoint.equals(bar.rightTopPoint);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar)
            return this.equals(other) || !(((Bar) other).leftBotPoint.getX() > this.rightTopPoint.getX()
                    || ((Bar) other).leftBotPoint.getY() > this.rightTopPoint.getY()
                    || ((Bar) other).rightTopPoint.getX() < this.leftBotPoint.getX()
                    || ((Bar) other).rightTopPoint.getY() < this.leftBotPoint.getY());
        if (other instanceof Point)
            return ((Point) other).getX() >= this.leftBotPoint.getX()
                    && ((Point) other).getX() <= this.rightTopPoint.getX()
                    && ((Point) other).getY() >= this.leftBotPoint.getY()
                    && ((Point) other).getY() <= this.rightTopPoint.getY();
        throw new NotImplementedException();
    }
}