package geometry;

public class Bar implements Collider {

    private Point up;
    private Point down;

    public Bar(int firstX, int firstY, int secondX, int secondY) {

        if (firstX > secondX) {
            firstX = secondX + firstX;
            secondX = firstX - secondX;
            firstX = firstX - secondX;
        }
        if (firstY > secondY) {
            firstY = secondY + firstY;
            secondY = firstY - secondY;
            firstY = firstY - secondY;
        }
        this.up = new Point(firstX, firstY);
        this.down = new Point(secondX, secondY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bar bar = (Bar) o;
        if (this.up.equals(bar.getUp()) && this.down.equals(bar.getDown())) {
            return true;
        } else {
            return false;
        }
    }


    public Point getUp() {
        return this.up;
    }

    public Point getDown() {
        return this.down;
    }

    public boolean isPointOnBar(Point point) {
        return (point.getX() >= this.up.getX()) && (point.getX() <= this.down.getX())
                && (point.getY() >= this.up.getY()) && (point.getY() <= this.down.getY());
    }

    public boolean isColliding(Collider bar) {
        if (bar instanceof Point) {
            return (this.up.getX() >= ((Point) bar).getX() && this.down.getY() <= ((Point) bar).getY()
                    && this.down.getX() <= ((Point) bar).getX() && this.up.getY() >= ((Point) bar).getY());
        } else if (bar instanceof Bar) {
            return (bar.isColliding(this.down)
                    || this.isColliding(((Bar) bar).getDown()) || bar.isColliding(this.up)
                    || this.isColliding(((Bar) bar).getUp()));
        }

        return false;
    }

}
