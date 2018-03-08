package ru.atom.geometry;

public class Bar implements Collider {
    private Point firstCorner;
    private Point secondCorner;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.setFirstCorner(new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY)));
        this.setSecondCorner(new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY)));
    }

    public Point getFirstCorner() {
        return firstCorner;
    }

    public void setFirstCorner(Point firstCorner) {
        this.firstCorner = firstCorner;
    }

    public Point getSecondCorner() {
        return secondCorner;
    }

    public void setSecondCorner(Point secondCorner) {
        this.secondCorner = secondCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar other = (Bar) o;

        return other.getFirstCorner().equals(this.getFirstCorner())
                && other.getSecondCorner().equals(this.getSecondCorner());
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return ((Point) other).getX() >= this.getFirstCorner().getX()
                    && ((Point) other).getX() <= this.getSecondCorner().getX()
                    && ((Point) other).getY() >= this.getFirstCorner().getY()
                    && ((Point) other).getY() <= this.getSecondCorner().getY();

        } else if (other instanceof Bar) {
            if (((Bar) other).getSecondCorner().getY() < this.getFirstCorner().getY()
                    || ((Bar) other).getFirstCorner().getY() > this.getSecondCorner().getY()
                    || ((Bar) other).getFirstCorner().getX() > this.getSecondCorner().getX()
                    || ((Bar) other).getSecondCorner().getX() < this.getFirstCorner().getX()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
