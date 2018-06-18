package ru.atom.geometry;

public class Bar implements Collider {
    private Point leftPoint;
    private Point rightPoint;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int leftX = firstCornerX < secondCornerX ? firstCornerX : secondCornerX;
        int rightX = leftX == firstCornerX ? secondCornerX : firstCornerX;
        int topY = firstCornerY > secondCornerY ? firstCornerY : secondCornerY;
        int bottomY = topY == firstCornerY ? secondCornerY : firstCornerY;

        leftPoint = new Point(leftX, topY);
        rightPoint = new Point(rightX, bottomY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (leftPoint != null ? !leftPoint.equals(bar.leftPoint) : bar.leftPoint != null) return false;
        return rightPoint != null ? rightPoint.equals(bar.rightPoint) : bar.rightPoint == null;
    }

    @Override
    public int hashCode() {
        int result = leftPoint != null ? leftPoint.hashCode() : 0;
        result = 31 * result + (rightPoint != null ? rightPoint.hashCode() : 0);
        return result;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other)) {
            return true;
        }

        if (other instanceof Point) {
            Point point = (Point) other;
            return point.getX() >= leftPoint.getX() && rightPoint.getX() >= point.getX()
                    && leftPoint.getY() >= point.getY() && rightPoint.getY() <= point.getY();
        }

        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            Border thisFirst = new Border(new Point(leftPoint.getX(), 0), new Point(rightPoint.getX(), 0));
            Border thisSecond = new Border(new Point(0, rightPoint.getY()), new Point(0, leftPoint.getY()));

            Border otherFirst = new Border(new Point(bar.leftPoint.getX(), 0), new Point(bar.rightPoint.getX(), 0));
            Border otherSecond = new Border(new Point(0, bar.rightPoint.getY()), new Point(0, bar.leftPoint.getY()));

            return thisFirst.isColliding(otherFirst) && thisSecond.isColliding(otherSecond);
        }
        return other.isColliding(this);
    }

    private static class Border implements Collider {
        private final Point pointOne;
        private final Point pointTwo;

        public Border(Point pointOne, Point pointTwo) {
            this.pointOne = pointOne;
            this.pointTwo = pointTwo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Border border = (Border) o;

            if (pointOne != null ? !pointOne.equals(border.pointOne) : border.pointOne != null) return false;
            return pointTwo != null ? pointTwo.equals(border.pointTwo) : border.pointTwo == null;
        }

        @Override
        public boolean isColliding(Collider other) {
            if (this.equals(other)) return true;

            if (other instanceof Point) {
                Point point = (Point) other;

                if (pointOne.getX() == pointTwo.getX()) {
                    if (point.getX() != pointOne.getX()) {
                        return false;
                    }

                    int bottom = pointOne.getY() < pointTwo.getY()
                            ? pointOne.getY() : pointTwo.getY();

                    int top = pointOne.getY() == bottom
                            ? pointTwo.getY() : pointOne.getY();

                    return point.getY() >= bottom && point.getY() <= top;
                } else if (pointOne.getY() == pointTwo.getY()) {
                    if (point.getY() != pointOne.getY()) {
                        return false;
                    }

                    int left = pointOne.getX() < pointTwo.getX()
                            ? pointOne.getX() : pointTwo.getX();

                    int right = pointOne.getX() == left
                            ? pointTwo.getX() : pointOne.getX();

                    return point.getX() >= left && point.getX() <= right;
                }
            }

            if (other instanceof Border) {
                Border border = (Border) other;

                if (this.getLeft().getX() > border.getRight().getX()
                        || this.getBottom().getY() > border.getTop().getY()) {
                    return false;
                }
                if (!(this.getRight().getX() < border.getLeft().getX()
                        || this.getTop().getY() < border.getBottom().getY())) {
                    return true;
                } else return false;
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
