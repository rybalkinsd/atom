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
            public Point move(Point point, int velocity) {
                return new Point(point.getX(), point.getY() + velocity);
            }
        }, DOWN {
            @Override
            public Point move(Point point, int velocity) {
                return new Point(point.getX(), point.getY() - velocity);
            }
        }, LEFT {
            @Override
            public Point move(Point point, int velocity) {
                return new Point(point.getX() - velocity, point.getY());
            }
        }, RIGHT {
            @Override
            public Point move(Point point, int velocity) {
                return new Point(point.getX() + velocity, point.getY());
            }
        }, IDLE {
            @Override
            public Point move(Point point, int velocity) {
                return point;
            }
        };

        public abstract Point move(Point point, int velocity);
    }
}
