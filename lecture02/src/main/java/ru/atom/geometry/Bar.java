package ru.atom.geometry;

public class Bar implements Collider {
    Point firstCornerPoint; // left bottom
    Point secondCornerPoint; // right up

    public Bar(int xFirst, int yFirst, int xSecond, int ySecond) {
        int firstCornerX = Math.min(xFirst, xSecond);
        int secondCornerX = Math.max(xFirst, xSecond);
        int firstCornerY = Math.min(yFirst, ySecond);
        int secondCornerY = Math.max(yFirst, ySecond);
        firstCornerPoint = new Point(firstCornerX, firstCornerY);
        secondCornerPoint = new Point(secondCornerX, secondCornerY);
    }

    public boolean isColliding(Collider o) {
        if (getClass() == o.getClass()) {
            Bar bar = (Bar) o;
            Point firstPoint = new Point(bar.firstCornerPoint.x, bar.secondCornerPoint.y);
            Point secondPoint = new Point(bar.secondCornerPoint.x, bar.firstCornerPoint.y);
            boolean isInThisBar = pointInsideBar(firstPoint) || pointInsideBar(secondPoint);
            boolean isInThatBar = bar.pointInsideBar(firstCornerPoint) || bar.pointInsideBar(secondCornerPoint);
            return isInThisBar || isInThatBar;
        } else {
            return pointInsideBar((Point) o);
        }
    }

    public boolean pointInsideBar(Point point) {
        boolean isRight = this.firstCornerPoint.x <= point.x && this.firstCornerPoint.y <= point.y;
        boolean isLeft = point.x <= secondCornerPoint.x && point.y <= secondCornerPoint.y;
        return isLeft && isRight;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;
        return firstCornerPoint.equals(bar.firstCornerPoint) && secondCornerPoint.equals(bar.secondCornerPoint);
    }

    /* public boolean isColliding(Bar other) {
        return this.pointInsideBar(other.firstCornerPoint) ||
               this.pointInsideBar(other.secondCornerPoint);
    } */
}
