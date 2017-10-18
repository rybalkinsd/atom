package ru.atom.model;


import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Girl implements Movable {

    private static final Logger logger = LogManager.getLogger(Girl.class);
    private int id;
    private Point position;
    private int speed = 1;


    public Girl(Point position, int id) {
        this.id = id;
        this.position = position;
        logger.info("Girl is created: id = {} x = {} y = {} speed = {}",
                getId(), position.getX(), position.getY());
    }

    @Override
    public Point move(Direction direction, long time) {
        int distance = (int) (speed * time);
        switch (direction) {
            case UP:
                this.position = new Point(this.position.getX(), this.position.getY() + distance);
                break;
            case DOWN:
                this.position = new Point(this.position.getX(), this.position.getY() - distance);
                break;
            case RIGHT:
                this.position = new Point(this.position.getX() + distance, this.position.getY());
                break;
            case LEFT:
                this.position = new Point(this.position.getX() - distance, this.position.getY());
                break;
            case IDLE:
                this.position = new Point(this.position.getX(), this.position.getY());
                break;
            default:
                return  this.position;

        }


        return this.position;
    }

    @Override
    public Point getPosition() {

        return position;
    }

    @Override
    public int getId() {

        return id;
    }

    @Override
    public void tick(long elapsed) {

        logger.info("tick{}",elapsed);

    }
}
