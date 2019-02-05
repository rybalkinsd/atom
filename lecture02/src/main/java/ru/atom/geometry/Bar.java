package ru.atom.geometry;

import java.util.Arrays;

public class Bar implements Collider {
    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
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

        Bar bar = (Bar) o;
        int[] currentBar = new int[]{
                Math.min(firstCornerX, secondCornerX),
                Math.min(firstCornerY, secondCornerY),
                Math.max(firstCornerX, secondCornerX),
                Math.max(firstCornerY, secondCornerY)};

        int[] otherBar = new int[]{
                Math.min(bar.firstCornerX, bar.secondCornerX),
                Math.min(bar.firstCornerY, bar.secondCornerY),
                Math.max(bar.firstCornerX, bar.secondCornerX),
                Math.max(bar.firstCornerY, bar.secondCornerY)};

        if (Arrays.equals(currentBar, otherBar)) return true;
        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            if (point.getX() >= firstCornerX
                    && point.getX() <= secondCornerX && point.getY() >= firstCornerY
                    && point.getY() <= secondCornerY) {
                return true;
            } else {
                return false;
            }
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            int[] currentBar = new int[]{
                    Math.min(firstCornerX, secondCornerX),
                    Math.min(firstCornerY, secondCornerY),
                    Math.max(firstCornerX, secondCornerX),
                    Math.max(firstCornerY, secondCornerY)};

            int[] otherBar = new int[]{
                    Math.min(bar.firstCornerX, bar.secondCornerX),
                    Math.min(bar.firstCornerY, bar.secondCornerY),
                    Math.max(bar.firstCornerX, bar.secondCornerX),
                    Math.max(bar.firstCornerY, bar.secondCornerY)};
            if ((otherBar[0] <= currentBar[2])
                    && (currentBar[0] <= otherBar[2])
                    && (otherBar[1] <= currentBar[3])
                    && (currentBar[1] <= otherBar[3])) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
