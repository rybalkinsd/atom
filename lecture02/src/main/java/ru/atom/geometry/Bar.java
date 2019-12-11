package ru.atom.geometry;

public class Bar implements Collider {

    private final int leftX;
    private final int bottomY;
    private final int rightX;
    private final int topY;

    public Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.leftX = Math.min(firstCornerX, secondCornerX);
        this.bottomY = Math.min(firstCornerY, secondCornerY);
        this.rightX = Math.max(firstCornerX, secondCornerX);
        this.topY = Math.max(firstCornerY, secondCornerY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Bar)) {
            return false;
        }
        Bar other = (Bar) obj;
        return this.leftX == other.leftX && this.bottomY == other.bottomY
                && this.rightX == other.rightX && this.topY == other.topY;
    }

    /**
     * Checks if val is inside range
     *
     * @param val of interest
     * @param left boundary
     * @param right boundary
     * @return true if val is inside else false
     */
    private boolean isInside(int val, int left, int right) {
        return val >= left && val <= right;
    }

    public int width() {
        return rightX - leftX;
    }

    public int height() {
        return topY - bottomY;
    }

    private int centerX() {
        return leftX + (rightX - leftX) / 2;
    }

    private int centerY() {
        return bottomY + (topY - bottomY) / 2;
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            Point point = (Point) other;
            return isInside(point.getX(), leftX, rightX) && isInside(point.getY(), bottomY, topY);
        }
        if (other instanceof Bar) {
            Bar bar = (Bar) other;
            int meanWidth = (this.width() + bar.width()) / 2;
            int meanHeight = (this.height() + bar.height()) / 2;
            int centersXDist = Math.abs(this.centerX() - bar.centerX());
            int centersYDist = Math.abs(this.centerY() - bar.centerY());
            return centersXDist <= meanWidth && centersYDist <= meanHeight;
        }
        return false;
    }
}
