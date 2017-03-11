package ru.atom.geometry;


public class Bar implements Collider {

    private int firstCornerX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    Bar() {
    }

    Bar(int x1, int y1, int x2, int y2) {
        defineFirstSecondCorner(x1, y1, x2, y2);
    }

    private void defineFirstSecondCorner(int x1, int y1, int x2, int y2) {
        if (x1 < x2) {
            firstCornerX = x1;
            secondCornerX = x2;
        } else {
            firstCornerX = x2;
            secondCornerX = x1;
        }
        if (y1 < y2) {
            firstCornerY = y1;
            secondCornerY = y2;
        } else {
            firstCornerY = y2;
            secondCornerY = y1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        if (firstCornerX == bar.firstCornerX && firstCornerY == bar.firstCornerY && secondCornerX == bar.secondCornerX
                && secondCornerY == bar.secondCornerY) {
            return true;
        } else {
            return false;
        }

        //return isColliding(bar);
        //throw new NotImplementedException();
    }

    @Override
    public boolean isColliding(Collider other) {

        if (other == null) {
            return false;
        }

        if (other.getClass() == Point.class) {
            return isColliding((Point) other);
        } else {
            if (this == other)
                return true;
            if (other == null || getClass() != other.getClass())
                return false;

            return isColliding((Bar) other);
        }

    }

    public boolean isColliding(Bar bar) {
        if (equals(bar)) return true;

        Point p1 = new Point(bar.firstCornerX, bar.firstCornerY);
        Point p2 = new Point(bar.secondCornerX, bar.secondCornerY);

        if (isColliding(p1) || isColliding(p2)) {
            return true;
        }

        return false;
    }

    public boolean isColliding(Point point) {

        if (point.x <= secondCornerX && point.x >= firstCornerX && point.y >= firstCornerY
                && point.y <= secondCornerY) {
            return true;
        } else
            return false;

    }
}
