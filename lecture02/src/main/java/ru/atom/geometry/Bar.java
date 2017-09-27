package ru.atom.geometry;

public class Bar implements Collider {
    private final Point top;
    private final Point bottom;

    public Point getTop() {
        return this.top;
    }

    public Point getBottom() {
        return this.bottom;
    }

    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.top = new Point(Math.max(firstCornerX, secondCornerX), Math.max(firstCornerY, secondCornerY));
        this.bottom = new Point(Math.min(firstCornerX, secondCornerX), Math.min(firstCornerY, secondCornerY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Bar
        Bar bar = (Bar) o;
        if (this.top.equals(bar.getTop()) && this.bottom.equals(bar.getBottom())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isColliding(Collider o) {
        if (o instanceof Point) {
            return (this.top.getX() >= ((Point) o).getX() && this.top.getY() >= ((Point) o).getY()
                    && this.bottom.getX() <= ((Point) o).getX() && this.bottom.getY() <= ((Point) o).getY());
        } else if (o instanceof Bar) {
            return (this.isColliding(((Bar) o).getTop()) || this.isColliding(((Bar) o).getBottom())
                    || o.isColliding(this.top) || o.isColliding(this.bottom));
        }
        return false;
    }
}
