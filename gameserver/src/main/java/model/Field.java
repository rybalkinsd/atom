package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Field {

    static final int LENGTH = 10000;
    static final int WIDTH = 10000;

    @NotNull
    private static final Logger log = LogManager.getLogger(Field.class);
    @NotNull
    private Set<Cell> cells;
    @NotNull
    private Set<Food> foods;
    @NotNull
    private List<Virus> viruses;
    @Nullable
    private List<Blob> blobs = new ArrayList<>();

    public Field(@NotNull Set<Cell> cells, @NotNull Set<Food> foods, @NotNull List<Virus> viruses) {
        this.cells = cells;
        this.foods = foods;
        this.viruses = viruses;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    public void addCells(Set<Cell> cells) {
        for (Cell elem:
             cells) {
            this.cells.add(elem);
        }
    }

    @Override
    public String toString() {
        return "Field{" +
                "length=" + LENGTH +
                ", width=" + WIDTH +
                ", players=" + this.cells +
                ", foods=" + this.foods +
                ", viruses=" + this.viruses +
                ", blobs=" + this.blobs +
                '}';
    }
}
