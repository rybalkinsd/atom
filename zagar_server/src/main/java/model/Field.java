package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.EatComparator;
import utils.quadTree.QuadTree;
import utils.quadTree.TreePoint;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author apomosov
 */
public class Field {
    @NotNull
    private static final Logger log = LogManager.getLogger(Field.class);
    @NotNull
    private final Dimension2D fieldSize;
    @NotNull
    private final Rectangle2D fieldRange;
    @NotNull
    private final QuadTree<Cell> entities;
    @NotNull
    private EatComparator eatComparator = new EatComparator();


    public Field(@NotNull Dimension2D fieldSize) {
        this.fieldSize=fieldSize;
        this.fieldRange = new Rectangle2D.Double(0, 0, fieldSize.getWidth(), fieldSize.getHeight());
        this.entities = new QuadTree<>(fieldRange);
    }

    @NotNull
    public <T extends Cell> List<T> getCells(Class<T> identifier) {
        return entities.getAllPointsWhere(point->
                point.getItem().isPresent() &&
                        identifier.isInstance(point.getItem().get())
        )
                .stream()
                .map(point->point.getItem().get())
                .map(identifier::cast)
                .collect(Collectors.toList());
    }

    @NotNull
    List<PlayerCell> getPlayerCells(@NotNull Player player) {
        return entities.getAllPointsWhere(point->
                point.getItem().isPresent() &&
                        PlayerCell.class.isInstance(point.getItem().get()) &&
                        PlayerCell.class.cast(point.getItem().get()).getOwner().equals(player)
        )
                .stream()
                .map(point->point.getItem().get())
                .map(PlayerCell.class::cast)
                .collect(Collectors.toList());
    }

    public void addCell(@NotNull Cell cell) {
        log.info("Field:{} Added {} to ({}, {})",
                toString(), cell.getClass().getName(), cell.getCoordinate().getX(), cell.getCoordinate().getY());
        entities.set(cell.getCoordinate(),cell);
    }

    public void removeCell(@NotNull Cell cell) {
        log.trace("Removing {} from ({}, {})",
                cell.getClass().getName(), cell.getCoordinate().getX(), cell.getCoordinate().getY());
        entities.remove(cell.getCoordinate());
    }


    public void moveCell(@NotNull Cell cell, @NotNull Point2D newCoordinate) {
        removeCell(cell);
        cell.setCoordinate(newCoordinate);
        addCell(cell);
    }

    @NotNull
    public List<Cell> findIntersected(@NotNull Cell cell) {
        return entities.searchWithin(cell.getBox()).stream()
                .map(TreePoint::getItem)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @NotNull
    public Rectangle2D getRegion(){
        return fieldRange;
    }

    @NotNull
    public List<Cell> getAllCells() {
        return entities.getAllPoints().stream()
                .filter(tp->tp.getItem().isPresent())
                .map(tp->tp.getItem().get())
                .collect(Collectors.toList());
    }

    @NotNull
    public Dimension2D getSize() {
        return fieldSize;
    }
}
