package geometry;

public class Point implements Collider {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;
        if (this.x == point.getX() && this.y == point.getY()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isColliding(Collider point) {
        if (point instanceof Point) {
            return this.equals(point);
        } else if (point instanceof Bar) {
            return point.isColliding(this);
        }
        return false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
