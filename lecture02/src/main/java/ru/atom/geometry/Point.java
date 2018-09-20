package ru.atom.geometry;

public class Point implements Collider {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Point)) {
            return false;
        }

        Point other = (Point) object;
        return this.x == other.x && this.y == other.y;
    }

    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return this.equals(other);
        } else if (other instanceof Bar) {
            return this.x >= ((Bar)other).getLeftBottom().getX()
                    && this.x <= ((Bar)other).getRightTop().getX()
                    && this.y >= ((Bar)other).getLeftBottom().getY()
                    && this.y <= ((Bar)other).getRightTop().getY();
        } else {
            return false;
        }
    }
}