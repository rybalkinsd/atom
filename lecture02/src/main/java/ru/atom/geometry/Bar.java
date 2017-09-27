package ru.atom.geometry;

public class Bar implements Collider {

    int firstCornerX;
    int secondCornerX;
    int firstCornerY;
    int secondCornerY;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;
        boolean x1 = firstCornerX == bar.firstCornerX;
        boolean y1 = firstCornerY == bar.firstCornerY;
        boolean x2 = secondCornerX == bar.secondCornerX;
        boolean y2 = secondCornerY == bar.secondCornerY;
        return x1 && y1 && x2 && y2;

    }

    public boolean isColliding(Collider o) {
        if (o instanceof Point) {
            Point point = (Point) o;
            boolean x1 = ((point.x >= firstCornerX) && (point.x <= secondCornerX));
            boolean y1 = ((point.y >= firstCornerY) && (point.y <= secondCornerY));
            return x1 && y1;
        }
        if (o instanceof Bar) {
            Bar bar = (Bar) o;
            boolean x1 = ((bar.firstCornerX >= firstCornerX) && (bar.firstCornerX <= secondCornerX));
            boolean x2 = ((bar.secondCornerX >= firstCornerX) && (bar.secondCornerX <= secondCornerX));
            boolean y1 = ((bar.firstCornerY >= firstCornerY) && (bar.firstCornerY <= secondCornerY));
            boolean y2 = ((bar.secondCornerY >= firstCornerY) && (bar.secondCornerY <= secondCornerY));
            return (x1 || x2) && (y1 || y2);
        }
        return false;
    }

    public Bar(int x1, int y1, int x2, int y2) {

        int minX = Math.min(x1,x2);
        firstCornerX = minX;
        int minY = Math.min(y1,y2);
        firstCornerY = minY;
        int maxX = Math.max(x1,x2);
        secondCornerX = maxX;
        int maxY = Math.max(y1,y2);
        secondCornerY = maxY;

    }
}
