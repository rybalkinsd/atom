package ru.atom.geometry;

/**
 * Created by Western-Co on 01.03.2017.
 */
public class Bar implements Collider {
    private int firstPointX;
    private int firstCornerY;
    private int secondCornerX;
    private int secondCornerY;

    public Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firstPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Bar thisBar = orientiedBar(this);
        Bar curBar = orientiedBar((Bar) o);
        if ((thisBar.firstPointX == curBar.firstPointX)
            && (thisBar.firstCornerY == curBar.firstCornerY)
            && (thisBar.secondCornerX == curBar.secondCornerX)
            && (thisBar.secondCornerY == curBar.secondCornerY)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isColliding(Collider other) {
        if (other.getClass() == this.getClass()) {
            //if (instanceOf)
            Bar thisBar = orientiedBar(this);
            Bar otherBar = orientiedBar((Bar) other);
            if (thisBar.firstPointX > otherBar.secondCornerX
                    || thisBar.firstCornerY > otherBar.secondCornerY
                    || thisBar.secondCornerX < otherBar.firstPointX
                    || thisBar.secondCornerY < otherBar.firstCornerY) {
                return  false;
            } else {
                return true;
            }
        } else if (other.getClass() == Point.class) {
            Point otherPoint = (Point) other;
            if ((firstPointX <= otherPoint.x && otherPoint.x <= secondCornerX)
                && (firstCornerY <= otherPoint.y && otherPoint.y <= secondCornerY)) {
                return  true;
            }
        }
        return false;
    }

    private Bar orientiedBar(Bar toOrientied) {
        int firstX = Math.min(toOrientied.firstPointX, toOrientied.secondCornerX);
        int firstY = Math.min(toOrientied.firstCornerY, toOrientied.secondCornerY);
        int secondX = Math.max(toOrientied.firstPointX, toOrientied.secondCornerX);
        int secondY = Math.max(toOrientied.firstCornerY, toOrientied.secondCornerY);
        Bar bar = new Bar(firstX, firstY, secondX, secondY);
        return bar;
    }
}
