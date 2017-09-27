package ru.atom.geometry;

public class Bar implements Collider {

    private Point up, down;

    Bar(int firstX, int firstY, int secondX, int secondY) {

        if (firstX < secondX) {
            firstX = secondX + firstX;
            secondX = firstX - secondX;
            firstX = firstX - secondX;
        }
        if (firstY < secondY) {
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
        if (this.up.equals(bar.returnUp()) && this.down.equals(bar.returnDown())) {
            return true;
        } else {
            return false;
        }

    }

    public Point returnUp() {
        return this.up;
    }

    public Point returnDown() {
        return this.down;
    }

    public boolean isColliding(Collider bar) {
        if (bar instanceof Point) {
            return (this.up.returnX() >= ((Point) bar).returnX() && this.down.returnY() <= ((Point) bar).returnY()
                    && this.down.returnX() <= ((Point) bar).returnX() && this.up.returnY() >= ((Point) bar).returnY());
        } else if (bar instanceof Bar) {
            return (bar.isColliding(this.down)
                    || this.isColliding(((Bar) bar).returnDown()) || bar.isColliding(this.up)
                    || this.isColliding(((Bar) bar).returnUp()));
        }

        return false;
    }

}


