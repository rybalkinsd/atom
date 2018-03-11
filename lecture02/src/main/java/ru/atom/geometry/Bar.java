package ru.atom.geometry;

public class Bar implements Collider {

    private final Point leftDownPoint;
    private final Point rigthUpPoint;

    public Point getLeftDownPoint() {
        return leftDownPoint;
    }

    public Point getRigthUpPoint() {
        return rigthUpPoint;
    }

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.leftDownPoint = new Point(Math.min(firstCornerX,secondCornerX),Math.min(firstCornerY,secondCornerY));
        this.rigthUpPoint = new Point(Math.max(firstCornerX,secondCornerX),Math.max(firstCornerY,secondCornerY));
    }


    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar other1 = (Bar) other;
            return (getLeftDownPoint().getX() <= other1.getRigthUpPoint().getX()
                    && getLeftDownPoint().getY() <= other1.getRigthUpPoint().getY()
                    && getRigthUpPoint().getX() >= other1.getLeftDownPoint().getX()
                    && getRigthUpPoint().getY() >= other1.getLeftDownPoint().getY());
        }
        return other instanceof Point && other.isColliding(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;

        if (bar.getLeftDownPoint().equals(this.getLeftDownPoint()))
            if (bar.getRigthUpPoint().equals(this.getRigthUpPoint())) return true;
        return false;
        //throw new UnsupportedOperationException();
    }
}
