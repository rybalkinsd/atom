package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public static final int BORDER_DOWN = 0;
    public static final int BORDER_TOP = 20000;
    public static final int BORDER_LEFT = 0;
    public static final int BORDER_RIGHT = 20000;

    private static final Logger log = LogManager.getLogger(Field.class);

    @NotNull
    private List<Cell> cells = new ArrayList<>();

    @NotNull
    private List<Food> foods;

    @NotNull
    private List<Virus> viruses;

    @NotNull
    private List<Blob> blobs = new ArrayList<>();

    public Field(@NotNull List<Food> foods, @NotNull List<Virus> viruses) {
        this.foods = foods;
        this.viruses = viruses;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(@NotNull List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Field{" +
                "cells=" + cells +
                ", foods=" + foods +
                ", viruses=" + viruses +
                ", blobs=" + blobs +
                '}';
    }
}
