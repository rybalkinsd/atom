package ru.atom.geometry;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Bar implements Collider {
    private final int firstX;
    private final int firstY;
    private final int secondX;
    private final int secondY;

    public Bar(int firstX, int firstY, int secondX, int secondY) {
        this.firstX = min(firstX, secondX);
        this.firstY = min(firstY, secondY);
        this.secondX = max(firstX, secondX) ;
        this.secondY = max(firstY, secondY);
    }

    public int getFirstX() {
        return firstX;
    }

    public int getFirstY() {
        return firstY;
    }

    public int getSecondX() {
        return secondX;
    }

    public int getSecondY() {
        return secondY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (firstX != bar.firstX) return false;
        if (firstY != bar.firstY) return false;
        if (secondX != bar.secondX) return false;
        return secondY == bar.secondY;
    }

    @Override
    public int hashCode() {
        int result = firstX;
        result = 31 * result + firstY;
        result = 31 * result + secondX;
        result = 31 * result + secondY;
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return checkPointCollision((Point) other);
        }
        if (other instanceof Bar) {
            return checkBarCollision((Bar) other);
        }
        return false;
    }

    private boolean checkBarCollision(Bar other) {
        return other.secondY >= firstY
                && other.firstY <= secondY
                && other.secondX >= firstX
                && other.firstX <= secondX;
    }

    private boolean checkPointCollision(Point other) {
        return other.getX() >= firstX && other.getX() <= secondX
                && other.getY() >= firstY && other.getY() <= secondY;
    }

}
