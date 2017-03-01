package ru.atom.geometry;

/**
 * Created by gammaker on 01.03.2017.
 */
public class Bar implements Collider {
    public int left;
    public int bottom;
    public int width;
    public int height;

    public Bar(int x1, int y1, int x2, int y2) {
        left = x1;
        bottom = y1;
        width = x2 - x1;
        height = y2 - y1;
        if (width < 0) {
            left += width;
            width = -width;
        }
        if (height < 0) {
            bottom += height;
            height = -height;
        }
    }

    @Override
    public boolean isColliding(Collider other) {
        if (other instanceof Point) {
            return isColliding((Point) other);
        }
        if (other instanceof Bar) {
            return isColliding((Bar) other);
        }
        return false;
    }

    boolean isColliding(Point point) {
        boolean intersectsX = point.x >= left && point.x <= left + width;
        boolean intersectsY = point.y >= bottom && point.y <= bottom + height;
        return intersectsX && intersectsY;
    }

    boolean isColliding(Bar bar) {
        if (left > bar.left+bar.width ||
            left+width < bar.left ||
            bottom+height < bar.bottom ||
            bottom > bar.bottom+bar.height) {
            return false;
        }
        return true;
    }

    @Override public boolean equals(Object other) {
        if(!(other instanceof Bar)) {
            return false;
        }
        Bar bar = (Bar) other;
        return left == bar.left && bottom == bar.bottom &&
                width == bar.width && height == bar.height;
    }
}
