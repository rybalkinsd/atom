package ru.atom.geometry;

/**
 * Template class for
 */
public class Bar implements Collider {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    private boolean intersection(int a1, int a2, int b1, int b2) {
        return !((Math.min(a1,a2) > Math.max(b1,b2)) || (Math.min(b1,b2) > Math.max(a1,a2)));
    }

    /**
     * @param o - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;

        return ((((bar.x1 == x1) && (bar.x2 == x2)) || ((bar.x1 == x2) && (bar.x2 == x1)))
                && (((bar.y1 == y1) && (bar.y2 == y2)) || ((bar.y1 == y2) && (bar.y2 == y1))));
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            return (intersection(bar.x1,bar.x2,x1,x2) && intersection(bar.y1,bar.y2,y1,y2));
        } else {
            Point point = (Point) other;
            return ((point.x >= Math.min(x1, x2)) && (point.x <= Math.max(x1, x2))
                    && (point.y >= Math.min(y1, y2)) && (point.y <= Math.max(y1, y2)));
        }
    }

    public Bar(int xx1, int yy1, int xx2, int yy2) {
        this.x1 = xx1;
        this.x2 = xx2;
        this.y1 = yy1;
        this.y2 = yy2;
    }
}
