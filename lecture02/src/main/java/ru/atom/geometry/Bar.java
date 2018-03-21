package ru.atom.geometry;

public class Bar implements Collider {
    private Point point1;
    private Point point2;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        point1 = new Point(firstCornerX, firstCornerY);
        point2 = new Point(secondCornerX, secondCornerY);
    }

    /**
     * @param o - other object to check equality with
     * @return true if two bars are equal and not null.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;
        return true;
    }

    @Override
    public boolean isColliding(Collider other) {
        if(other instanceof Point) {
            Point point = (Point) other;
            return (this.point1.getX() <= point.getX() && this.point2.getX() >= point.getX()) &&
                    (this.point1.getY() <= point.getY() && this.point2.getY() >= point.getY());
        }
        Bar bar = (Bar) other;
        return (bar.point1.getX() <= this.point2.getX() && bar.point2.getX() >= this.point1.getX()) &&
               (bar.point1.getY() <= this.point2.getY() && bar.point2.getY() >= this.point1.getY());
    }
}
