package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
    private static final @NotNull Logger log = LogManager.getLogger(PlayerCell.class);
    private final int id;
    @NotNull
    private AtomicLong lastMovementTime = new AtomicLong(System.currentTimeMillis());
    @NotNull
    private Player owner;

    public PlayerCell(@NotNull Player owner, int id, @NotNull Point2D coordinate, int mass) {
        super(coordinate, mass);
        this.id = id;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setCoordinate(@NotNull Point2D newCoordinate) {
        super.setCoordinate(newCoordinate);
        lastMovementTime.set(System.currentTimeMillis());
    }

    public void eat(Cell cell) {
        log.info("I AM IN EAT");
        this.setMass(this.getMass() + cell.getMass());
        this.getOwner().updateScore(cell.getMass());
        if (cell instanceof PlayerCell) {
            ((PlayerCell) cell).getOwner().updateScore(-cell.getMass());
        }
        log.info("I AM Removing Cell");
        getOwner().getField().removeCell(cell);
        //todo check remove last cell of another player??
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

    public boolean ejectMass() {
        if (getMass() > GameConstants.MASS_TO_EJECT) {
            getOwner().updateScore(-GameConstants.MASS_TO_EJECT);
            setMass(getMass() - GameConstants.MASS_TO_EJECT);
            EjectedMass ejectedMass = new EjectedMass(
                    new Point2D.Double(getCoordinate().getX() + getLastMovement().getX() / 4,
                            getCoordinate().getY() + getLastMovement().getY() / 4),
                    new Point2D.Double(getCoordinate().getX() * 3, getCoordinate().getY() * 3),
                    GameConstants.MASS_TO_EJECT,
                    GameConstants.INITIAL_SPEED,
                    GameConstants.EJECTED_MASS_ACCELERATION
            );
            getOwner().getField().addCell(ejectedMass);
            return true;
        }
        return false;
    }

    public boolean split() {
        if (this.getMass() / getOwner().getCells().size() > GameConstants.MASS_TO_SPLIT) {
            // int size = getOwner().getCells().size()*2;
            int size = 2;
            int mass = this.getMass() / size;
            double r = getRadius();
            List<Cell> cells = new ArrayList<>();
            double x = getCoordinate().getX();
            double y = getCoordinate().getY();
            for (int i = 0; i < size; i++) {
                cells.add(new PlayerCell(getOwner(), getId() + i + 1,
                        new Point2D.Double(x + i * 6 * mass,
                                y), mass));
            }
            getOwner().getCells().forEach(c -> {
                if (getId() == c.getId())
                getOwner().getField().removeCell(c);
            });
            cells.forEach(c -> getOwner().getField().addCell(c));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PlayerCell that = (PlayerCell) o;

        if (id != that.id) return false;
        if (!lastMovementTime.equals(that.lastMovementTime)) return false;
        return owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + lastMovementTime.hashCode();
        result = 31 * result + owner.hashCode();
        return result;
    }
}
