package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.CellQuadTree;

import java.awt.*;

/**
 * @author apomosov
 *
 * Describes game cell
 */
public abstract class Cell {
    private int x;
    private int y;
    private int radius;
    private int mass;
    @Nullable
    private CellQuadTree node;

    public Cell(int x, int y, int mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        updateRadius();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
        updateRadius();
    }

    @Nullable
    public CellQuadTree getNode() {
        return node;
    }

    public void setNode(@Nullable CellQuadTree node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return x != cell.x &&
                y != cell.y &&
                radius != cell.radius &&
                mass == cell.mass;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + radius;
        result = 31 * result + mass;
        return result;
    }

    @NotNull
    public Rectangle getRange() {
        return new Rectangle(x, y, radius, radius);
    }

    private void updateRadius() {
        this.radius = (int) Math.sqrt(this.mass / Math.PI);
    }
}
