package ru.atom.geometry;

/**
 * Created by Юля on 02.03.2017.
 */
public final class Bar implements Collider /* super class and interfaces here if necessary */ {
    private final Point maxPoint;
    private final Point minPoint;// fields


    Bar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        maxPoint = new Point(getMaxCoordinate(firstCornerX, secondCornerX),
                getMaxCoordinate(firstCornerY, secondCornerY));
        minPoint = new Point(getMinCoordinate(firstCornerX, secondCornerX),
                getMinCoordinate(firstCornerY, secondCornerY));
    }

    /**
     * @param other - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Bar bar = (Bar) other;
        if (maxPoint.equals(bar.maxPoint) && minPoint.equals(bar.minPoint)) {
            return true;
        }
        return false;//throw new NotImplementedException();
    }


    @Override
    public boolean isColliding(Collider other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) {
            Point point = (Point) other;
            return checkPointInside(point.x, point.y, minPoint, maxPoint);
        }
        Bar bar = (Bar) other;
        if ((checkPointInside(bar.minPoint.getX(), bar.minPoint.getY(), minPoint, maxPoint))
                || (checkPointInside(bar.maxPoint.getX(), bar.maxPoint.getY(), minPoint, maxPoint))) {
            return true;
        }
        return false;
        //throw new NotImplementedException();
    }

    private int getMaxCoordinate(int firstCoordinate, int secondCoordinate) {
        if (firstCoordinate > secondCoordinate) {
            return firstCoordinate;
        } else {
            return secondCoordinate;
        }
    }

    private int getMinCoordinate(int firstCoordinate, int secondCoordinate) {
        if (firstCoordinate < secondCoordinate) {
            return firstCoordinate;
        } else {
            return secondCoordinate;
        }
    }


    private boolean checkPointInside(int x, int y, Point minPoint, Point maxPoint) {
        return checkCoordinateInside(x, minPoint.getX(), maxPoint.getX())
                && checkCoordinateInside(y, minPoint.getY(), maxPoint.getY());
    }

    private boolean checkCoordinateInside(int pointCoordinate, int barMinCoordinate, int barMaxCoordinate) {
        return (pointCoordinate <= barMaxCoordinate) && (pointCoordinate >= barMinCoordinate);
    }
}

