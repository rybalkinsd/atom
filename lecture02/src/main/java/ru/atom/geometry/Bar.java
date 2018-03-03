package ru.atom.geometry;

public class Bar implements Collider {
    private final Point firstCorner;
    private final Point secondCorner;

    public Bar(int firstCornerX, int firstCornerY,
               int secondCornerX, int secondCornerY) {
        int xmax = (firstCornerX > secondCornerX) ? firstCornerX : secondCornerX;
        int xmin = (firstCornerX < secondCornerX) ? firstCornerX : secondCornerX;
        int ymax = (firstCornerY > secondCornerY) ? firstCornerY : secondCornerY;
        int ymin = (firstCornerY < secondCornerY) ? firstCornerY : secondCornerY;

        firstCorner = new Point(xmin, ymin);
        secondCorner = new Point(xmax, ymax);
    }

    public Point getFirstCorner() {
        return firstCorner;
    }

    public Point getSecondCorner() {
        return secondCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        Point barFirstCorner = bar.getFirstCorner();
        Point barSecondCorner = bar.getSecondCorner();

        if (firstCorner.equals(barFirstCorner))
            return secondCorner.equals(barSecondCorner);
        else if (firstCorner.equals(barSecondCorner))
            return secondCorner.equals(barFirstCorner);

        return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return point.isColliding(this);
        } else if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Point firstCornerBar = bar.getFirstCorner();
            Point secondCornerBar = bar.getSecondCorner();
            return (firstCornerBar.getX() <= secondCorner.getX()
                    && secondCornerBar.getX() >= firstCorner.getX()
                    && secondCorner.getY() >= firstCornerBar.getY()
                    && secondCornerBar.getY() >= firstCorner.getY());
        }
        return false;
    }
}
