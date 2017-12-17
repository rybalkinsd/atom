package bomber.games.geometry;

public class Bar implements Collider {

    public final int firstCornerX;
    public final int secondCornerX;

    public final int secondCornerY;
    public final int firstCornerY;

    public Bar(int x1, int x2, int y1, int y2) {
        int minX = Math.min(x1, x2);
        firstCornerX = minX;
        int minY = Math.min(y1, y2);
        firstCornerY = minY;
        int maxX = Math.max(x1, x2);
        secondCornerX = maxX;
        int maxY = Math.max(y1, y2);
        secondCornerY = maxY;
    }

    public int getFirstCornerX() {
        return firstCornerX;
    }

    public int getSecondCornerX() {
        return secondCornerX;
    }

    public int getFirstCornerY() {
        return firstCornerY;
    }

    public int getSecondCornerY() {
        return secondCornerY;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            boolean x1 = ((point.getX() >= firstCornerX) && (point.getX() <= secondCornerX));
            boolean y1 = ((point.getY() >= firstCornerY) && (point.getY() <= secondCornerY));
            return x1 && y1;
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            boolean x1 = ((bar.firstCornerX >= firstCornerX) && (bar.firstCornerX <= secondCornerX));
            boolean x2 = ((bar.secondCornerX >= firstCornerX) && (bar.secondCornerX <= secondCornerX));
            boolean y1 = ((bar.firstCornerY >= firstCornerY) && (bar.firstCornerY <= secondCornerY));
            boolean y2 = ((bar.secondCornerY >= firstCornerY) && (bar.secondCornerY <= secondCornerY));
            return (x1 || x2) && (y1 || y2);
        }
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else {
            if (obj instanceof Bar) {
                Bar bar = (Bar) obj;
                if (this.firstCornerX == bar.getFirstCornerX() && this.secondCornerX == bar.getSecondCornerX()
                        && this.firstCornerY == bar.getFirstCornerY() && this.secondCornerY == bar.getSecondCornerY())
                    return true;
                if (this.firstCornerX == bar.getSecondCornerX() && this.firstCornerY == bar.getSecondCornerY()
                        && this.secondCornerX == bar.getFirstCornerX() && this.secondCornerY == bar.getFirstCornerY())
                    return true;
                if (this.firstCornerX == bar.getSecondCornerX() && this.firstCornerY == bar.getFirstCornerY()
                        && this.secondCornerX == bar.getFirstCornerX() && this.secondCornerY == bar.getSecondCornerY())
                    return true;
                if (this.firstCornerX == bar.getSecondCornerX() && this.firstCornerY == bar.getFirstCornerY()
                        && this.secondCornerX == bar.getFirstCornerX() && this.secondCornerY == bar.getSecondCornerY())
                    return true;
            }
            return false;
        }
    }
}
