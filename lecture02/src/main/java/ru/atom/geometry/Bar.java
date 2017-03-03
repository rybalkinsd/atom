package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Western-Co on 01.03.2017.
 */
public class Bar implements Collider {
    private int firstPointX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firstPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    public int getFirstPointX() {
        return firstPointX;
    }

    public void setFirstPointX(int firstPointX) {
        this.firstPointX = firstPointX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public void setFirstCornerY(int firstCornerY) {
        this.firstCornerY = firstCornerY;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public void setSecondCornerX(int secondCornerX) {
        this.secondCornerX = secondCornerX;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    public void setSecondCornerY(int secondCornerY) {
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Bar thisBar = orientiedBar(this);
        Bar curBar = orientiedBar((Bar) o);
        if ((thisBar.firstPointX == curBar.firstPointX)
            && (thisBar.firstCornerY == curBar.firstCornerY)
            && (thisBar.secondCornerX == curBar.secondCornerX)
            && (thisBar.secondCornerY == curBar.secondCornerY)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == this.getClass()) {
            Bar thisBar = orientiedBar(this);
            Bar otherBar = orientiedBar((Bar) other);
            if (thisBar.firstPointX > otherBar.secondCornerX
                    || thisBar.firstCornerY > otherBar.secondCornerY
                    || thisBar.secondCornerX < otherBar.firstPointX
                    || thisBar.secondCornerY < otherBar.firstCornerY) {
                return  false;
            } else {
                return true;
            }
        } else if (other.getClass() == Point.class) {
            Point otherPoint = (Point) other;
            if ((firstPointX <= otherPoint.getX() && otherPoint.getX() <= secondCornerX)
                && (firstCornerY <= otherPoint.getY() && otherPoint.getY() <= secondCornerY)) {
                return  true;
            }
        } else {
            throw new NotImplementedException();
        }
        return false;
    }

    private Bar orientiedBar(Bar toOrientied) {
        int firstX = Math.min(toOrientied.getFirstPointX(), toOrientied.getSecondCornerX());
        int firstY = Math.min(toOrientied.getFirstCornerY(), toOrientied.getSecondCornerY());
        int secondX = Math.max(toOrientied.getFirstPointX(), toOrientied.getSecondCornerX());
        int secondY = Math.max(toOrientied.getFirstCornerY(), toOrientied.getSecondCornerY());
        Bar bar = new Bar(firstX, firstY, secondX, secondY);
        return bar;
    }
}
