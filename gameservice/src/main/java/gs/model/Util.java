package gs.model;

import gs.geometry.Point;

public final class Util {
    private Util() {
    }

    public static void generateMap(GameSession session) {
        for (int x = 0; x < 17; x++) {
            addWall(session, new Point(x, 0));
            addWall(session, new Point(x, 12));
        }
        for (int y = 1; y < 12; y++) {
            addWall(session, new Point(0, y));
            addWall(session, new Point(16, y));
        }
        for (int x = 2; x < 15; x++) {
            for (int y = 2; y < 11; y++) {
                if (x % 2 == 0 && y % 2 == 0) {
                    addWall(session, new Point(x, y));
                } else if (!((x == 1 || x == 15) && (y == 1 || y == 2 || y == 10 || y == 11)
                        || (x == 2 || x == 14) && (y == 1 || y == 11))) {
                    addBrick(session, new Point(x, y));
                }
            }
        }
        for (int i = 3; i < 10; i++) {
            addBrick(session, new Point(15, i));
            addBrick(session, new Point(1, i));
        }
        for (int i = 3; i < 14; i++) {
            addBrick(session, new Point(i, 11));
            addBrick(session, new Point(i, 1));
        }
    }

    private static void addBrick(GameSession session, Point position) {
        session.addGameObject(new Brick(session, position));
    }

    private static void addWall(GameSession session, Point position) {
        session.addGameObject(new Wall(session, position));
    }
}
