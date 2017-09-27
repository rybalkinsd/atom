package ru.atom.geometry;

public class Bar implements Collider {
    private Point point1;
    private Point point2;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.point1 = new Point(firstCornerX, firstCornerY);
        this.point2 = new Point(secondCornerX, secondCornerY);
    }

    public boolean checkIn(int x, int y) {
        return (x >= point1.getX() && x <= point2.getX() && y >= point1.getY() && y <= point2.getY());
    }

    public boolean checkEq(int x, int y) {
        return (x == point1.getX() && y == point1.getY() || x == point2.getX() && y == point2.getY());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar)
            return (checkIn(((Bar)other).point1.getX(), ((Bar)other).point1.getY())
                || checkIn(((Bar)other).point1.getX(), ((Bar)other).point2.getY())
                || checkIn(((Bar)other).point2.getX(), ((Bar)other).point1.getY())
                || checkIn(((Bar)other).point2.getX(), ((Bar)other).point2.getY()));
        else if (other instanceof Point)
            return (checkIn(((Point)other).getX(),((Point)other).getY()));
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return (checkEq(((Bar)o).point1.getX(), ((Bar)o).point1.getY())
            || checkEq(((Bar)o).point2.getX(), ((Bar)o).point2.getY())
            || checkEq(((Bar)o).point2.getX(), ((Bar)o).point1.getY())
            || checkEq(((Bar)o).point2.getX(), ((Bar)o).point2.getY()));
    }
}
