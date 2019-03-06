package ru.atom.geometry;

public class Bar implements Collider {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public Bar(int x0, int y0, int x1, int y1) {
        left = Math.min(x0, x1);
        right = Math.max(x0, x1);
        top = Math.max(y0, y1);
        bottom = Math.min(y0, y1);
    }

    public int getWidth( ) {
        return right - left;
    }

    public int getHeight( ) {
        return top - bottom;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return point.isColliding(this);
        }

        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            if (equals(bar))
                return true;

            if (left > (bar.getLeft() + bar.getWidth()) || (left + getWidth()) < bar.getLeft()) return false;
            if (bottom > (bar.getBottom() + bar.getHeight()) || (bottom + getHeight()) < bar.getBottom()) return false;
            return true;
        }

        return false;
    }

    /*Function RectsOverlap:Bool(x1:Float, y1:Float, w1:Float, h1:Float, x2:Float, y2:Float, w2:Float, h2:Float)
        If x1 > (x2 + w2) Or (x1 + w1) < x2 Then Return False
        If y1 > (y2 + h2) Or (y1 + h1) < y2 Then Return False
        Return True
    End Function*/

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

        // your code here
        return  top == bar.getTop() && bottom == bar.getBottom() && left == bar.getLeft() && right == bar.getRight();
    }

    public int getLeft() {
        return left;
    }


    public int getTop() {
        return top;
    }



    public int getRight() {
        return right;
    }



    public int getBottom() {
        return bottom;
    }

}
