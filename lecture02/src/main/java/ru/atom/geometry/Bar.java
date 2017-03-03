package ru.atom.geometry;

import java.util.stream.Stream;

/**
 * Created by dmitriy on 01.03.17.
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

    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == Point.class) {
            Point point = (Point) other;
            return (Math.min(this.firstPointX, this.secondCornerX) <= point.getX()
                    && point.getX() <= Math.max(this.firstPointX, this.secondCornerX)
                    && Math.min(this.firstCornerY, this.secondCornerY) <= point.getY()
                    && point.getY() <= Math.max(this.firstCornerY, this.secondCornerY));
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
        int minVertical = Stream.of(bar.firstCornerY, bar.secondCornerY, this.firstCornerY, this.secondCornerY)
                .min(Integer::compareTo).get();
        int maxVertical = Stream.of(bar.firstCornerY, bar.secondCornerY, this.firstCornerY, this.secondCornerY)
                .max(Integer::compareTo).get();
        int minHorizontal = Stream.of(bar.firstPointX, bar.secondCornerX, this.firstPointX, this.secondCornerX)
                .min(Integer::compareTo).get();
        int maxHorizontal = Stream.of(bar.firstPointX, bar.secondCornerX, this.firstPointX, this.secondCornerX)
                .max(Integer::compareTo).get();
        return (maxVertical - minVertical <= Math.abs(bar.firstCornerY - bar.secondCornerY)
                + Math.abs(this.firstCornerY - this.secondCornerY)
                && maxHorizontal - minHorizontal <= Math.abs(bar.firstPointX - bar.secondCornerX)
                + Math.abs(this.firstPointX - this.secondCornerX));
    }
}
