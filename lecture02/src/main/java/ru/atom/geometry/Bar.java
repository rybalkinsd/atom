package ru.atom.geometry;

public class Bar implements Collider {
    private final Point first;
    private final Point second;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int leftX = firstCornerX < secondCornerX ? firstCornerX : secondCornerX;
        int rightX = leftX == firstCornerX ? secondCornerX : firstCornerX;
        int topY = firstCornerY > secondCornerY ? firstCornerY : secondCornerY;
        int bottomY = topY == firstCornerY ? secondCornerY : firstCornerY;

        first = new Point(leftX, topY);
        second = new Point(rightX, bottomY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return getLeft().equals(bar.getLeft()) && getRight().equals(bar.getRight());
    }

    private Point getLeft() {
        return first.getX() < second.getX() ? first : second;
    }

    private Point getRight() {
        return first == getLeft() ? second : first;
    }

    private Point getBottom() {
        return first.getY() < second.getY() ? first : second;
    }

    private Point getTop() {
        return first == getBottom() ? second : first;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) return true;

        if (other instanceof Point) {
            Point point = (Point) other;
            return point.getX() >= getLeft().getX() && point.getX() <= getRight().getX()
                    && point.getY() >= getBottom().getY() && point.getY() <= getTop().getY();
        }

        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Segment thisFirst = new Segment(new Point(getLeft().getX(), 0), new Point(getRight().getX(), 0));
            Segment thisSecond = new Segment(new Point(0, getBottom().getY()), new Point(0, getTop().getY()));

            Segment otherFirst = new Segment(new Point(bar.getLeft().getX(), 0), new Point(bar.getRight().getX(), 0));
            Segment otherSecond = new Segment(new Point(0, bar.getBottom().getY()), new Point(0, bar.getTop().getY()));

            return thisFirst.isColliding(otherFirst) && thisSecond.isColliding(otherSecond);
        }
        return other.isColliding(this);
    }

    private static class Segment implements Collider {
        private final Point pointOne;
        private final Point pointTwo;

        public Segment(Point pointOne, Point pointTwo) {
            this.pointOne = pointOne;
            this.pointTwo = pointTwo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Segment segment = (Segment) o;

            if (pointOne != null ? !pointOne.equals(segment.pointOne) : segment.pointOne != null) return false;
            return pointTwo != null ? pointTwo.equals(segment.pointTwo) : segment.pointTwo == null;
        }

        @Override
        public boolean isColliding(Collider other) {
            if (this.equals(other)) return true;

            if (other instanceof Point) {
                Point point = (Point) other;

                if (pointOne.getX() == pointTwo.getX()) {
                    if (point.getX() != pointOne.getX()) return false;

                    int bottom = pointOne.getY() < pointTwo.getY()
                            ? pointOne.getY() : pointTwo.getY();

                    int top = pointOne.getY() == bottom
                            ? pointTwo.getY() : pointOne.getY();

                    return point.getY() >= bottom && point.getY() <= top;
                } else if (pointOne.getY() == pointTwo.getY()) {
                    if (point.getY() != pointOne.getY()) return false;

                    int left = pointOne.getX() < pointTwo.getX()
                            ? pointOne.getX() : pointTwo.getX();

                    int right = pointOne.getX() == left
                            ? pointTwo.getX() : pointOne.getX();

                    return point.getX() >= left && point.getX() <= right;
                }
            }

            if (other instanceof Segment) {
                Segment segment = (Segment) other;

                if (this.getLeft().getX() > segment.getRight().getX()
                        || this.getBottom().getY() > segment.getTop().getY()) {
                    return false;
                }
                if (this.getRight().getX() < segment.getLeft().getX()
                        || this.getTop().getY() < segment.getBottom().getY()) {
                    return false;
                }

                return true;
            }
            return other.isColliding(this);
        }

        private Point getLeft() {
            return pointOne.getX() < pointTwo.getX() ? pointOne : pointTwo;
        }

        private Point getRight() {
            return pointOne == getLeft() ? pointTwo : pointOne;
        }

        private Point getBottom() {
            return pointOne.getY() < pointTwo.getY() ? pointOne : pointTwo;
        }

        private Point getTop() {
            return pointOne == getBottom() ? pointTwo : pointOne;
        }
    }
}
