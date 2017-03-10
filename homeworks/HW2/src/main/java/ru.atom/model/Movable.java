package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * GameObject that can move during game
 */
public interface Movable extends Positionable, Tickable {
    /**
     * Tries to move entity towards specified direction
     * @return final position after movement
     */
    Point move(Direction direction);

    enum Direction {
        UP {
            @Override
            public Point move (Point point,int motion) {
                return new Point(point.getX(), point.getY() + motion);
            }
        }, DOWN {
            @Override
            public Point move (Point point, int motion) {
                return new Point(point.getX(), point.getY() - motion);
            }
        }, RIGHT {
            @Override
            public Point move (Point point,int motion) {
                return new Point(point.getX() + motion, point.getY());
            }
        }, LEFT {
            @Override
            public Point move (Point point,int motion) {
                return new Point(point.getX() - motion, point.getY() + motion);
            }
        }, IDLE {
                @Override
                public Point move (Point point,int motion) {
                    return point;
            }
        };

        public abstract Point move(Point point, int motion);
    }


}
