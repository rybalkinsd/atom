package gs.model;


import gs.tick.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gs.geometry.Point;

public class Pawn extends Field implements Movable, Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point point;
    private long currentTime = 0;
    private final int speed = 32;
    private boolean firstBonus = false;
    private int speedMultiply = 1; //Бонус на ускорение.
    private final int bonusTime = 10000; //Время действия бонуса
    private boolean powerful = false; //True если взял бонус на силу
    private boolean pyromancer = false; //True есси бонус огня

    private boolean dead = false;

    public Pawn(int x, int y) {
        super(x, y);
        this.id = getId();
        this.point = getPosition();
        log.info("Playerid = " + id + "; " + "Pawn place = (" + point.getX() + "," +
                point.getY() + ")" + "; ");
    }

    public boolean isPowerful() {
        return powerful;
    }

    @Override
    public Point getPosition() {
        return super.getPosition();
    }

    public void setCurrentTime() {
        this.currentTime = 0;
    }

    public Point getPoint() {
        return point;
    }

    public boolean isBonusOver() {
        return bonusTime <= currentTime;
    }

    public void setPoint(int x, int y) {
        this.point = new Point(x, y);
    }

    public void setDead() {
        dead = true;
    }

    public boolean isFirstBonus() {
        return firstBonus;
    }

    public void setFirstBonus(boolean firstBonus) {
        this.firstBonus = firstBonus;
    }

    public void setPower() {
        powerful = true;
    }

    public void setSpeedMultiply(int acceleration) {
        this.speedMultiply = acceleration;
    }

    public int getSpeedMultiply() {
        return speedMultiply;
    }

    public void setPyromancer() {
        pyromancer = true;
    }

    public boolean isPyromancer() {
        return pyromancer;
    }

    public void restoreDefault() {
        speedMultiply = 1;
        powerful = false;
        pyromancer = false;
    }

    @Override
    public Point move(Direction direction, long time) {
        switch (direction) {
            case UP:
                point = new Point(point.getX(), point.getY() + (int) (speed * time));
                break;
            case DOWN:
                point = new Point(point.getX(), point.getY() - (int) (speed * time));
                break;
            case RIGHT:
                point = new Point(point.getX() + (int) (speed * time), point.getY());
                break;
            case LEFT:
                point = new Point(point.getX() - (int) (speed * time), point.getY());
                break;
            default:
                break;
        }
        log.info("id={} moved to({},{})", id, point.getX(), point.getY());
        return point;
    }

    @Override
    public void tick(long elapsed) {
        currentTime += elapsed;
    }

    public String toJson() {
        return "{\"type\":\"" + this.getClass().getSimpleName() + "\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + point.getX() + ",\"y\":" + point.getY() + "}}";
    }
}
