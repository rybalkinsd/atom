package ru.atom.geometry;


public class Bar implements Collider {
    private Point leftButtonPoint;
    private Point rightTopPoint;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int maxX = Math.max(firstCornerX, secondCornerX);
        int minX = Math.min(firstCornerX, secondCornerX);
        int maxY = Math.max(firstCornerY, secondCornerY);
        int minY = Math.min(firstCornerY, secondCornerY);
        leftButtonPoint = new Point(minX, minY);
        rightTopPoint = new Point(maxX, maxY);
    }

    public Point getLeftButtonPoint() {
        return leftButtonPoint;
    }

    public Point getRightTopPoint() {
        return rightTopPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // cast from Object to Point
        Bar bar = (Bar) o;
        return (bar.getLeftButtonPoint().equals(leftButtonPoint)
                && bar.getRightTopPoint().equals(rightTopPoint))
                || (bar.getLeftButtonPoint().equals(rightTopPoint)
                && bar.getRightTopPoint().equals(leftButtonPoint));
        // your code here
//        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isColliding(Collider other) {
        if (this.equals(other))
            return true;
        return contentPoint(other);
    }

    private boolean contentPoint(Collider other) {
        if (other instanceof Point)
            return contentPoint((Point) other);
        if (other instanceof Bar)
            return isColliding((Bar) other);
        throw new UnsupportedOperationException();

    }

    private boolean isColliding(Bar bar) {

        return horizontalLineBetweenBar(bar) && verticalLineBetWinBar(bar);
    }

    private boolean verticalLineBetWinBar(Bar bar) {

        int thisLeftX = leftButtonPoint.getX();
        int thisRightX = rightTopPoint.getX();
        int barLeftX = bar.leftButtonPoint.getX();
        int barRightX = bar.rightTopPoint.getX();

        boolean isLeftLineBetweenBar = barLeftX >= thisLeftX
                && barLeftX <= thisRightX;

        boolean isLeftLineBetweenThis = thisLeftX >= barLeftX
                && thisLeftX <= barRightX;


        boolean isRightLineBetweenBar = barRightX >= thisLeftX
                && barRightX <= thisRightX;

        boolean isRightLineBetweenThis = thisRightX >= barLeftX
                && thisRightX <= barLeftX;

        return isLeftLineBetweenBar || isRightLineBetweenBar || isLeftLineBetweenThis || isRightLineBetweenThis;


    }

    private boolean horizontalLineBetweenBar(Bar bar) {
        int thisButtomY = leftButtonPoint.getY();
        int thisTopY = rightTopPoint.getY();
        int barButtomY = bar.leftButtonPoint.getY();
        int barTopY = bar.rightTopPoint.getY();

        boolean isButtomLineBetweenBar = barButtomY >= thisButtomY
                && barButtomY <= thisTopY;
        boolean isButtomLineBetweenThis = thisButtomY >= barButtomY
                && thisButtomY <= barTopY;

        boolean isTopLineBetweenBar = barTopY >= thisButtomY
                && barTopY <= thisTopY;

        boolean isTopLineBetweenThis = thisTopY >= barButtomY
                && thisTopY <= barTopY;

        return isButtomLineBetweenBar || isTopLineBetweenBar || isButtomLineBetweenThis || isTopLineBetweenThis ;
    }

    private boolean contentPoint(Point point) {

        return point.getX() >= leftButtonPoint.getX()
                && point.getX() <= rightTopPoint.getX()
                && point.getY() >= leftButtonPoint.getY()
                && point.getY() <= rightTopPoint.getY();
    }

}
