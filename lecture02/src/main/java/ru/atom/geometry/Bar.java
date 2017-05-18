package ru.atom.geometry;

public class Bar implements Collider {

    private  Point leftBottom;
    private  Point rightTop;

    public Bar(int x1, int y1, int x2, int y2) {
        Point leftPoint = new Point(0,0);
        Point rightPoint = new Point(0,0);
        if (x1 < x2) {
            leftPoint.setX(x1);
            rightPoint.setX(x2);
        } else {
            leftPoint.setX(x2);
            rightPoint.setX(x1);
        }
        if (y1 < y2) {
            leftPoint.setY(y1);
            rightPoint.setY(y2);
        } else {
            leftPoint.setY(y2);
            rightPoint.setY(y1);
        }
        this.setLeftBottom(leftPoint);
        this.setRightTop(rightPoint);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        if ((getLeftBottom().equals(bar.getLeftBottom())) && (getRightTop().equals(bar.getRightTop())))
            return true;
        else return false;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null) return false;
        if (other instanceof Point) {
            Point point = (Point) other;
            if ((point.getX() >= getLeftBottom().getX()) && (point.getX() <= getRightTop().getX())
                    && (point.getY() >= getLeftBottom().getY()) && (point.getY() <= getRightTop().getY()))
                    return true;
        }
        if (other.getClass() == getClass()) {
            Bar bar = (Bar) other;
            if ((bar.getLeftBottom().getX() > getRightTop().getX())
                    || (bar.getRightTop().getX() < getLeftBottom().getX())
                    || (bar.getRightTop().getY() < getLeftBottom().getY())
                    || (bar.getLeftBottom().getY() > getRightTop().getY()))
                    return  false;
            else return  true;
        }
        return false;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }
}
