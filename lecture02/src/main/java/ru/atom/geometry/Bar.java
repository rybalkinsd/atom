package ru.atom.geometry;

import java.util.Arrays;

/**
 * Created by BBPax on 01.03.17.
 */
public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.setBar(firstCornerX, firstCornerY, secondCornerX, secondCornerY);
    }

    /**constructor with changing any initialization to a standart form:
     *     ----------- *(2)
     *    |            |
     *    |            |
     *    |            |
     *    |            |
     *    |            |
     * (1)* -----------
    */

    public void setBar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstCornerX = Arrays.asList(firstCornerX, secondCornerX).stream().min(Integer::compareTo).get();
        this.firstCornerY = Arrays.asList(firstCornerY, secondCornerY).stream().min(Integer::compareTo).get();
        this.secondCornerX = Arrays.asList(firstCornerX,secondCornerX).stream().max(Integer::compareTo).get();
        this.secondCornerY = Arrays.asList(firstCornerY, secondCornerY).stream().max(Integer::compareTo).get();
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

    private boolean barBarCollision(Bar bar) {
        return this.getSecondCornerX() >= bar.getFirstCornerX() && bar.getSecondCornerX() >= this.getFirstCornerX()
                && this.getSecondCornerY() >= bar.getFirstCornerY() && bar.getSecondCornerY() >= this.getFirstCornerY();
    }

    public boolean isInsideBar(Point point) {
        return ((point.getY() >= this.getFirstCornerY() && point.getY() <= this.getSecondCornerY())
                && (point.getX() >= this.getFirstCornerX() && point.getX() <= this.getSecondCornerX()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        return (this.getFirstCornerX() == bar.getFirstCornerX() && this.getFirstCornerY() == bar.getFirstCornerY()
                && this.getSecondCornerX() == bar.getSecondCornerX()
                && this.getSecondCornerY() == bar.getSecondCornerY());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return this.isInsideBar(point);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return this.barBarCollision(bar);
        } else return false;
    }
}
