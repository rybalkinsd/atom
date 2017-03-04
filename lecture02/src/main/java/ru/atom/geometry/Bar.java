package ru.atom.geometry;

/**
 * Created by Юля on 02.03.2017.
 */
public class Bar implements Collider /* super class and interfaces here if necessary */ {
    int firstPointX;
    int firstCornerY;
    int secondCornerX;
    int secondCornerY;// fields

    // and methods
    Bar() {
    }

    Bar(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        this.firstPointX = firstPointX;
        this.firstCornerY = firstCornerY;
        this.secondCornerX = secondCornerX;
        this.secondCornerY = secondCornerY;
    }

    /**
     * @param other - other object to check equality with
     * @return true if two points are equal and not null.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Point maxPointNew = getMaxPoint(firstPointX, firstCornerY, secondCornerX, secondCornerY);
        Point minPointNew = getMinPoint(firstPointX, firstCornerY, secondCornerX, secondCornerY);
        Bar bar = (Bar) other;
        Point maxPointNewOther = getMaxPoint(bar.firstPointX, bar.firstCornerY, bar.secondCornerX, bar.secondCornerY);
        Point minPointNewOther = getMinPoint(bar.firstPointX, bar.firstCornerY, bar.secondCornerX, bar.secondCornerY);
        if (maxPointNew.equals(maxPointNewOther) && minPointNew.equals(minPointNewOther)) {
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
            return checkPointInside(point.x, point.y, firstPointX, firstCornerY, secondCornerX, secondCornerY);
        }
        Bar bar = (Bar) other;
        int maxXOther = getMaxCoordinate(bar.firstPointX, bar.secondCornerX);
        int maxYOther = getMaxCoordinate(bar.firstCornerY, bar.secondCornerY);
        int minXOther = getMinCoordinate(bar.firstPointX, bar.secondCornerX);
        int minYOther = getMinCoordinate(bar.firstCornerY, bar.secondCornerY);
        if ((checkPointInside(minXOther, minYOther, firstPointX, firstCornerY, secondCornerX, secondCornerY))
                || (checkPointInside(maxXOther, maxYOther, firstPointX, firstCornerY, secondCornerX, secondCornerY))) {
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

    //Правая верхняя точка
    private Point getMaxPoint(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int CoordinateX = getMaxCoordinate(firstPointX, secondCornerX);
        int CoordinateY = getMaxCoordinate(firstCornerY, secondCornerY);
        return new Point(CoordinateX, CoordinateY);
    }

    //Левая нижняя точка
    private Point getMinPoint(int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int CoordinateX = getMinCoordinate(firstPointX, secondCornerX);
        int CoordinateY = getMinCoordinate(firstCornerY, secondCornerY);
        return new Point(CoordinateX, CoordinateY);
    }

    private boolean checkPointInside(int x, int y, int firstPointX, int firstCornerY, int secondCornerX, int secondCornerY) {
        int maxCoordinateX = getMaxCoordinate(firstPointX, secondCornerX);
        int maxCoordinateY = getMaxCoordinate(firstCornerY, secondCornerY);
        int minCoordinateX = getMinCoordinate(firstPointX, secondCornerX);
        int minCoordinateY = getMinCoordinate(firstCornerY, secondCornerY);
        return checkCoordinateInside(x, minCoordinateX, maxCoordinateX) && checkCoordinateInside(y, minCoordinateY, maxCoordinateY);
    }

    private boolean checkCoordinateInside(int pointCoordinate, int barMinCoordinate, int barMaxCoordinate) {
        return (pointCoordinate <= barMaxCoordinate) && (pointCoordinate >= barMinCoordinate);
    }
}

