package ru.atom.geometry;

/**
 * Created by imakarycheva on 28.02.18.
 */
public class Bar implements Collider {

    Point firstCorner;
    Point secondCorner;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        firstCorner = new Point(firstCornerX, firstCornerY);
        secondCorner = new Point(secondCornerX, secondCornerY);
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return containsPoint((Point) other);
        } else if (other instanceof Bar) {
            Bar otherBar = (Bar) other;
            return containsPoint(otherBar.firstCorner)
                    || containsPoint(otherBar.secondCorner)
                    || otherBar.containsPoint(firstCorner)
                    || otherBar.containsPoint(secondCorner);
        }
        return false;
    }

    public boolean containsPoint(Point point) {
        return point.x <= Math.max(firstCorner.x, secondCorner.x)
                && point.x >= Math.min(firstCorner.x, secondCorner.x)
                && point.y <= Math.max(firstCorner.y, secondCorner.y)
                && point.y >= Math.min(firstCorner.y, secondCorner.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;
        Point thirdCorner = new Point(bar.firstCorner.x, bar.secondCorner.y);
        Point fourthCorner = new Point(bar.secondCorner.x, bar.firstCorner.y);
        return firstCorner.equals(bar.firstCorner) && secondCorner.equals(bar.secondCorner)
                || firstCorner.equals(bar.secondCorner) && secondCorner.equals(bar.firstCorner)
                || firstCorner.equals(thirdCorner) && secondCorner.equals(fourthCorner)
                || firstCorner.equals(fourthCorner) && secondCorner.equals(thirdCorner);
    }
}
