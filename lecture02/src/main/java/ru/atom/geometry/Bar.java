package ru.atom.geometry;

/**
 * Created by Даниил on 28.02.2018.
 */
public class Bar implements Collider {

    private final Point downLeftPoint;
    private final Point topRightPoint;

    public Point getDownLeftPoint() {
        return downLeftPoint;
    }

    public Point getTopRightPoint() {
        return topRightPoint;
    }


    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        double minX = firstCornerX <= secondCornerX ? firstCornerX : secondCornerX;
        double maxX = firstCornerX > secondCornerX ? firstCornerX : secondCornerX;
        double minY = firstCornerY <= secondCornerY ? firstCornerY : secondCornerY;
        double maxY = firstCornerY > secondCornerY ? firstCornerY : secondCornerY;
        this.downLeftPoint = new Point(minX,minY);
        this.topRightPoint = new Point(maxX,maxY);
    }

    public Bar(Point downLeftPoint, Point topRightPoint) {
        this.downLeftPoint = downLeftPoint;
        this.topRightPoint = topRightPoint;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar other1 = (Bar) other;
            return (getDownLeftPoint().getX() <= other1.getTopRightPoint().getX()
                    && getTopRightPoint().getX() >= other1.getDownLeftPoint().getX()
                    && getDownLeftPoint().getY() <= other1.getTopRightPoint().getY()
                    && getTopRightPoint().getY() >= other1.getDownLeftPoint().getY());
        }
        if (other instanceof Point) {
            return other.isColliding(this);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        return (bar.getDownLeftPoint().equals(this.getDownLeftPoint())
                && bar.getTopRightPoint().equals(this.getTopRightPoint()));
    }
}
