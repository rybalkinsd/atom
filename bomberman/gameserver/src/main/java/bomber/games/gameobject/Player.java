package bomber.games.gameobject;


import bomber.games.geometry.Point;
import bomber.games.model.Movable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;


public final class Player implements Movable, Comparable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Player.class);
    public static final double INITIAL_VELOCITY = 0.07;
    public static final double VELOCITY_BONUS = INITIAL_VELOCITY * 0.33;
    private Point position;
    private final int id;
    private double velocity;
    private int bombPower;
    private int maxBombs;
    private final String type = "Pawn";
    @JsonIgnore
    private int bombCount;
    @JsonIgnore
    private final int playerSize = 27;
    @JsonIgnore
    private long time;
    @JsonIgnore
    private boolean alive = true;

    public Player(final int id, final Point position) {
        this.id = id;
        this.position = position;
        this.bombPower = 1;
        this.velocity = INITIAL_VELOCITY;
        this.maxBombs = 1;
        this.bombCount = 0;
        log.info("Create player with id = " + id);
    }

    public int getBombCount() {
        return bombCount;
    }

    public void incBombCount() {
        this.bombCount++;
    }

    public void decBombCount() {
        this.bombCount--;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), (int) (position.getY() + velocity * playerSize));
                log.info("move UP");
                break;
            case DOWN:
                position = new Point(position.getX(), (int) (position.getY() - velocity * playerSize));
                log.info("move DOWN");
                break;

            case RIGHT:
                position = new Point((int) (position.getX() + velocity * playerSize), position.getY());
                log.info("move RIGHT");
                break;

            case LEFT:
                position = new Point((int) (position.getX() - velocity * playerSize), position.getY());
                log.info("move LEFT");

                break;

            case IDLE:
                log.info("don't move, only IDLE");
                break;

            default:
                break;
        }
        return position;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


    @Override
    public void tick(long elapsed) {
        time = elapsed;

    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public int getBombPower() {
        return bombPower;
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj instanceof Player) {
            Player player = (Player) obj;
            return this.id == player.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\nPlayer: {" +
                "\nid = " + id +
                "\nposition = " + position +
                "\nbombPower = " + bombPower +
                "\nvelocity = " + velocity +
                "\nmaxBombs = " + maxBombs +
                "\n}";
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setMaxBombs(int maxBombs) {
        this.maxBombs = maxBombs;
    }

    public void setBombPower(int bombPower) {
        this.bombPower = bombPower;
    }

    @Override
    public int compareTo(@NotNull Object o) {

        if (this.id == o.hashCode())
            return 0;
        else if (this.id > o.hashCode())
            return 1;
        else
            return -1;

    }
}