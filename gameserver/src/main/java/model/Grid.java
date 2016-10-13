package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by helen on 10.10.16.
 * @author helen
 * Square game field
 */
public class Grid {
    @NotNull
    protected static final Logger log = LogManager.getLogger(Grid.class);
    @NotNull
    public double gridSize;

    /**
     * Create new game field
     * @param gridSize size of field
     */
    public Grid(@NotNull double gridSize){
        this.setGridSize(gridSize);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    private void setGridSize(@NotNull double gridSize){
        this.gridSize = gridSize;
    }

    public double getGridSize(){
        return this.gridSize;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "size='" + getGridSize() + '\'' + '}';
    }
}