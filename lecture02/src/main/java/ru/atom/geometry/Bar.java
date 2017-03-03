package ru.atom.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Bar implements Collider {
    private final Point startPoint;
    private final Point endPoint;

    public Bar(int x1, int y1, int x2, int y2) {
        startPoint = new Point(Math.min(x1, x2), Math.min(y1, y2));
        endPoint = new Point(Math.max(x1, x2), Math.max(y1, y2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return isColliding((Collider) o);

        Bar bar = (Bar) o;

        return (this.startPoint.equals(bar.startPoint) && this.endPoint.equals(bar.endPoint));
    }

    @Override
    public boolean isColliding(Collider other) {
        return isIncludes(other) || isIntersects(other);
    }

    public boolean isIncludes(Collider collider) {
        if (collider instanceof Bar) {
            Bar bar = (Bar) collider;
            if (bar.startPoint.getX() >= this.startPoint.getX() && bar.startPoint.getY() >= this.startPoint.getY()
                    && bar.endPoint.getX() <= this.endPoint.getX() && bar.endPoint.getY() <= this.endPoint.getY()) {
                return true;
            }
            return false;
        }
        if (collider instanceof Point) {
            Point point = (Point) collider;
            if (point.getX() >= this.startPoint.getX() && point.getX() <= this.endPoint.getX()
                    && point.getY() >= this.startPoint.getY() && point.getY() <= this.endPoint.getY()) {
                return true;
            }
            return false;
        }
        throw new NotImplementedException();
    }

    public boolean isIntersects(Collider collider) {
        if (collider instanceof Bar) {
            Bar bar = (Bar) collider;
            if (this.isIncludes(bar.startPoint) || this.isIncludes(bar.endPoint)) {
                return true;
            }
            if (bar.startPoint.getX() >= this.startPoint.getX()
                    && bar.endPoint.getX() <= this.endPoint.getX()
                    && bar.startPoint.getY() <= this.startPoint.getY()
                    && bar.endPoint.getY() >= this.endPoint.getY()) {
                return true;
            }
            return false;
        }
        if (collider instanceof Point) {
            return false;
        }
        throw new NotImplementedException();
    }
}
