package model;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
    private final int id;

    @NotNull
    private AtomicLong lastMovementTime = new AtomicLong(System.currentTimeMillis());
    @NotNull
    private Player owner;

    public PlayerCell(@NotNull Player owner, int id, int x, int y) {
        super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
        this.id = id;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        lastMovementTime.set(System.currentTimeMillis());
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        lastMovementTime.set(System.currentTimeMillis());
    }

    public void eat(Cell cell) {
        this.setMass(this.getMass() + cell.getMass());
    }

    public void explode() {
        //todo explode
    }

    long getLastMovementTime() {
        return lastMovementTime.get();
    }

    @NotNull
    public Player getOwner() {
        return owner;
    }
}
