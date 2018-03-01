package ru.atom.geometry;

/**
 * Created by imakarycheva on 28.02.18.
 */
public class Bar implements Collider {

    private final int lowerLeftCornerX;
    private final int lowerLeftCornerY;
    private final int upperRightCornerX;
    private final int upperRightCornerY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        upperRightCornerX = Math.max(firstCornerX, secondCornerX);
        upperRightCornerY = Math.max(firstCornerY, secondCornerY);
        lowerLeftCornerX = Math.min(firstCornerX, secondCornerX);
        lowerLeftCornerY = Math.min(firstCornerY, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return containsPoint(point.x, point.y);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return containsPoint(bar.lowerLeftCornerX, bar.lowerLeftCornerY)
                    || containsPoint(bar.upperRightCornerX, bar.upperRightCornerY)
                    || bar.containsPoint(lowerLeftCornerX, lowerLeftCornerY)
                    || bar.containsPoint(upperRightCornerX, upperRightCornerY);
        }
        return false;
    }

    public boolean containsPoint(int x, int y) {
        return x <= upperRightCornerX && x >= lowerLeftCornerX && y <= upperRightCornerY && y >= lowerLeftCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;
        return lowerLeftCornerX == bar.lowerLeftCornerX && lowerLeftCornerY == bar.lowerLeftCornerY
                && upperRightCornerX == bar.upperRightCornerX && upperRightCornerY == bar.upperRightCornerY;
    }
}
