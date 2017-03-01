package ru.atom.geometry;

/**
 * Created by Auerbah on 01.03.2017.
 */


public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        if( firstCornerX > secondCornerX) {
            int buf = firstCornerX;
            firstCornerX = secondCornerX;
            secondCornerX = buf;
        }
        if(firstCornerY > secondCornerY) {
            int buf = firstCornerY;
            firstCornerY = secondCornerY;
            secondCornerY = buf;
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

    public Point getLeftBottomPoint() {
        return new Point(firstCornerX, firstCornerY);
    }

    public Point getRightTopPoint () {
        return new Point(secondCornerX, secondCornerY);
    }

    public Point getLeftTopPoint() {
        return new Point(firstCornerX, secondCornerY);
    }

    public Point getRightBottomPoint() {
        return new Point(secondCornerX, firstCornerY);
    }
    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        // your code here
        return this.firstCornerX == bar.firstCornerX
                && this.firstCornerY == bar.firstCornerY
                && this.secondCornerX == bar.secondCornerX
                && this.secondCornerY == bar.secondCornerY;
    }

    public boolean isColliding(Point point) {
        return ((this.firstCornerX <= point.getX()) && (this.firstCornerY <= point.getY()) &&
                ((this.secondCornerX >= point.getX()) && (this.secondCornerY >= point.getY())));
    }

    public boolean isColliding(Bar bar) {
       return !(this.getRightTopPoint().getX() < bar.getLeftBottomPoint().getX() ||
                bar.getRightTopPoint().getX() < this.getLeftBottomPoint().getX() ||
                this.getLeftBottomPoint().getY() > bar.getRightTopPoint().getY() ||
                bar.getLeftBottomPoint().getY() > this.getRightTopPoint().getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        if(other instanceof Bar) {
            return isColliding((Bar) other);
        }
        else if(other instanceof Point) {
            return isColliding((Point)other);
        }
        return false;
    }

}
