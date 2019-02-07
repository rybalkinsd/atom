package ru.atom.geometry;

public class Bar implements Collider {
    private Point topLeftPoint ;
    private Point botRightPoint;

    public Bar(int x1 , int y1 , int x2 , int y2) {
        topLeftPoint = new Point();
        botRightPoint = new Point();
        if (x1 > x2) {
            botRightPoint.setX(x1);
            topLeftPoint.setX(x2);
        } else {
            botRightPoint.setX(x2);
            topLeftPoint.setX(x1);
        }

        if (y1 > y2) {
            botRightPoint.setY(y2);
            topLeftPoint.setY(y1);
        } else {
            botRightPoint.setY(y1);
            topLeftPoint.setY(y2);
        }
    }

    public Point getTopLeftPoint() {
        return topLeftPoint;
    }

    public void setTopLeftPoint(Point topLeftPoint) {
        this.topLeftPoint = topLeftPoint;
    }

    public Point getBotRightPoint() {
        return botRightPoint;
    }

    public void setBotRightPoint(Point botRightPoint) {
        this.botRightPoint = botRightPoint;
    }

    private boolean anotherBarIntersectThis(Bar bar) {
        if (topLeftPoint.getX() > bar.getBotRightPoint().getX()
                || botRightPoint.getX() < bar.getTopLeftPoint().getX()
                || topLeftPoint.getY() < bar.getBotRightPoint().getY()
                || botRightPoint.getY() > bar.getTopLeftPoint().getY()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Bar) {
            return anotherBarIntersectThis((Bar) other);
        }
        if (other instanceof Point) {
            return other.isColliding(this);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return  topLeftPoint.equals(bar.getTopLeftPoint())
                && botRightPoint.equals(bar.getBotRightPoint());
    }
}
