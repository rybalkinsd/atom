package ru.atom.model;

        import ru.atom.geometry.Point;

        public class Boy extends AbstractGameObj implements Tickable, Movable {

            public Boy(int x, int y) {
                super(x, y);
            }

            public int velocity = 5;

            @Override
    public Point move(Direction direction, long time) {
                int corX = position.getX();
                int corY = position.getY();
                long distance = velocity * time;
                switch (direction) {
                        case UP:
                                corY += distance;
                                break;
                        case DOWN:
                                corY -= distance;
                                break;
                        case LEFT:
                                corX -= distance;
                                break;
                        case RIGHT:
                                corX += distance;
                                break;
                        case IDLE:
                                break;
                        default:
                        }
                return position = new Point(corX, corY);
            }

            @Override
    public void tick(long elapsed) {

                    }
}