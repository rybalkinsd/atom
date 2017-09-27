package ru.atom.geometry;


public class Bar implements Collider {
    Point leftBottomPoint;
    Point rightUpPoint;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        // Определяем нижнюю левую и правую верхнюю точки
        if (firstCornerX < secondCornerX && firstCornerY < secondCornerY) {
            leftBottomPoint = new Point(firstCornerX, firstCornerY);
            rightUpPoint = new Point(secondCornerX, secondCornerY);
        }
        if (firstCornerX > secondCornerX && firstCornerY < secondCornerY) {
            leftBottomPoint = new Point(secondCornerX, firstCornerY);
            rightUpPoint = new Point(firstCornerX, secondCornerY);
        }
        if (firstCornerX < secondCornerX && firstCornerY > secondCornerY) {
            leftBottomPoint = new Point(firstCornerX, secondCornerY);
            rightUpPoint = new Point(secondCornerX, firstCornerY) ;
        }
        if (firstCornerX > secondCornerX && firstCornerY > secondCornerY) {
            leftBottomPoint = new Point(secondCornerX, secondCornerY);
            rightUpPoint = new Point(firstCornerX, firstCornerY);
        }

    }

    public boolean isColliding(Collider c) {
        if (this == c)
            return true;
        if (c.getClass().equals(this.getClass())) {
            Bar bar = (Bar) c;
            // Вариант, когда Bars пересекаются
            if (this.rightUpPoint.getX() < bar.leftBottomPoint.getX())
                return false;
            if (this.leftBottomPoint.getX() > bar.rightUpPoint.getX())
                return false;
            if (this.rightUpPoint.getY() < bar.leftBottomPoint.getY())
                return false;
            if (this.leftBottomPoint.getY() > bar.rightUpPoint.getY())
                return false;
            return true;
        } else {
            Point point = (Point) c;
            //Point лежит на границах Bar
            if (point.getX() <= this.rightUpPoint.getX() && point.getX() >= this.leftBottomPoint.getX())
                if (point.getY() <= this.rightUpPoint.getY() && point.getY() >= this.leftBottomPoint.getY())
                    return true;
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        if (this.leftBottomPoint.equals(bar.leftBottomPoint) && this.rightUpPoint.equals(bar.rightUpPoint))
            return true;
        return false;
    }
}
