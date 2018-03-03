package ru.atom.geometry;

public class Bar implements Collider {

    private final Point bottomPoint;
    private final Point topPoint;

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.bottomPoint = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
        this.topPoint = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return !(bottomPoint.getY() > bar.topPoint.getY() || topPoint.getX() < bar.bottomPoint.getX()
                    || bottomPoint.getX() > bar.topPoint.getX() || topPoint.getY() < bar.bottomPoint.getY());

        } else if (other instanceof Point) {
            Point point = (Point) other;
            return point.getX() >= bottomPoint.getX() && point.getX() <= topPoint.getX()
                    && point.getY() >= bottomPoint.getY()
                    && point.getY() <= topPoint.getY();
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return bottomPoint.equals(bar.bottomPoint) && topPoint.equals(bar.topPoint);
    }
}
