package geometry;

public final class Geometry {

    private Geometry() {
    }

    public static Collider createBar(int firstCornerX, int firstCornerY, int secondCornerX, int secondCornerY) {
        return new Bar(firstCornerX, firstCornerY, secondCornerX, secondCornerY);
    }


    public static Collider createPoint(int x, int y) {

        return new Point(x, y);
    }
}
