package ru.atom.geometry;

import java.util.Arrays;

/**
 * Created by dmitriy on 01.03.17.
 */
public class Bar implements Collider {

    int firstPointX;
    int firstCornerY;
    int secondCornerX;
    int secondCornerY;

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firstPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point point = (Point) other;
            if (Math.min(this.firstPointX, this.secondCornerX) <= point.x
                    && point.x <= Math.max(this.firstPointX, this.secondCornerX)
                    && Math.min(this.firstCornerY, this.secondCornerY) <= point.y
                    && point.y <= Math.max(this.firstCornerY, this.secondCornerY))
                return true;
            else
                return false;
        } else {
            return equals(other);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;
        int minVertical = Arrays.asList(bar.firstCornerY, bar.secondCornerY, this.firstCornerY, this.secondCornerY)
                .stream().min(Integer::compareTo).get();
        int maxVertical = Arrays.asList(bar.firstCornerY, bar.secondCornerY, this.firstCornerY, this.secondCornerY)
                .stream().max(Integer::compareTo).get();
        int minHorizontal = Arrays.asList(bar.firstPointX, bar.secondCornerX, this.firstPointX, this.secondCornerX)
                .stream().min(Integer::compareTo).get();
        int maxHorizontal = Arrays.asList(bar.firstPointX, bar.secondCornerX, this.firstPointX, this.secondCornerX)
                .stream().max(Integer::compareTo).get();
        if (maxVertical - minVertical <= Math.abs(bar.firstCornerY - bar.secondCornerY)
                + Math.abs(this.firstCornerY - this.secondCornerY)
                && maxHorizontal - minHorizontal <= Math.abs(bar.firstPointX - bar.secondCornerX)
                + Math.abs(this.firstPointX - this.secondCornerX))
            return true;
        else
            return false;
    }
}
