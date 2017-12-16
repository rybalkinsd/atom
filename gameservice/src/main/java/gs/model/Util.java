package gs.model;

import gs.geometry.Point;
import java.util.Random;

public final class Util {
    private Util() {
    }

    private static int i = 1;
    private static final Random random = new Random();

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
        if(random.nextInt(100) < 16) {
            /*switch (i) {
                case 1 :
                    addBonus(session, position, Bonus.BonusType.SPEED);
                    break;
                case 2 :
                    addBonus(session, position, Bonus.BonusType.RANGE);
                    break;
                case 3 :
                    addBonus(session, position, Bonus.BonusType.BOMBS);
                    break;
                case 4 :
                    addBonus(session, position, Bonus.BonusType.SPEED);
                    i = 1;
                    break;
                default: break;
            }
            i++;*/
        }
    }

    private static void addWall(GameSession session, Point position) {
        session.addGameObject(new Wall(session, position));
    }

    private static void addBonus(GameSession session, Point position, Bonus.BonusType type) {
        session.addGameObject(new Bonus(session, position, type));
    }

}
