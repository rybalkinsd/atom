package ru.atom.geometry;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;
        if (bar.firstCornerX == this.firstCornerX && bar.firstCornerY ==  this.firstCornerY
               && bar.secondCornerX == this.secondCornerX && bar.secondCornerY == this.secondCornerY) {
            return true;
        } else if (bar.firstCornerX == this.secondCornerX && bar.firstCornerY == this.secondCornerY
                && bar.secondCornerX == this.firstCornerX && bar.secondCornerY == this.firstCornerY) {
            return true;
        } else if (bar.firstCornerX == this.secondCornerX && bar.firstCornerY == this.firstCornerY
                && bar.secondCornerX == this.firstCornerX && bar.secondCornerY == this.secondCornerY) {
            return true;
        } else if (bar.firstCornerX == this.firstCornerX && bar.firstCornerY == this.secondCornerY
                && bar.secondCornerX == this.secondCornerX && bar.secondCornerY == this.firstCornerY) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other instanceof Point) {
            Point point = (Point) other;
            if (point.xCoord >= this.firstCornerX && point.xCoord <= this.secondCornerX
                   && point.yCoord >= this.firstCornerY && point.yCoord <= this.secondCornerY) {
                return true;
            } else {
                return false;
            }
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (bar.equals(this)) {
                return true;
            } else {
                if (bar.firstCornerX > this.secondCornerX || bar.secondCornerY < this.firstCornerY
                        || bar.secondCornerX < this.firstCornerX || bar.firstCornerY > this.secondCornerY) {
                    return false;
                } else if (this.firstCornerX > bar.secondCornerX || this.secondCornerY < bar.firstCornerY
                        || this.secondCornerX < bar.firstCornerX || this.firstCornerY > this.secondCornerY) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
    }
}
