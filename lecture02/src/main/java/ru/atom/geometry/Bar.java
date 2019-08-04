package ru.atom.geometry;

public class Bar implements Collider {
    private Point downLeftPoint;
    private Point upRightPoint;

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (this.downLeftPoint.getX() >= bar.downLeftPoint.getX()
                    && this.upRightPoint.getX() <= bar.upRightPoint.getX()
                    && this.downLeftPoint.getY() >= bar.downLeftPoint.getY()
                    && this.upRightPoint.getY() <= bar.upRightPoint.getY())
                    return true;
            else return false;
        } else {
                Point point = (Point) other;
                if (this.downLeftPoint.getX() <= point.getX()
                        && this.upRightPoint.getX() >= point.getX()
                        && this.downLeftPoint.getY() <= point.getY()
                        && this.upRightPoint.getY() >= point.getY())
                        return true;
                else return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

            Bar bar = (Bar) o;

            if (this. downLeftPoint == bar. downLeftPoint && this.upRightPoint == bar.upRightPoint) {
                    return true;
                } else return false;

    }

    public void setDownLeftPoint(Point downLeftPoint) {
        this.downLeftPoint = downLeftPoint;
    }

    public void setUpRightPoint(Point upRightPoint) {
        this.upRightPoint = upRightPoint;
    }
}

