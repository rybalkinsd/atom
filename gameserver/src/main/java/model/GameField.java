package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klissan on 09.10.2016.
 */

//have size from 0 to maxX and from 0 to maxY

/**
 * Describe game field with size  = maxX * maxY
 * Aggregates game entities of current game
 */
public class GameField {
    @NotNull
    private final Logger log = LogManager.getLogger(GameField.class);

    private double maxX;
    private double maxY;
    private List<GameEntity> gameEntities;//mb better realise as 2d-tree

    /**
     * Square field constructor
     *
     * @param fieldSize - a size of square field
     */
    GameField(double fieldSize ){
        maxX = maxY = fieldSize;
        gameEntities = new ArrayList<>();
        if (log.isInfoEnabled()) {
            log.info("Has been generated " + this);
        }
    };



    void add(GameEntity entity){
        this.gameEntities.add(entity);
    }

    @Override
    public String toString(){
        return "Game Field: { " + " sizeX = [0;" + maxX + "] sizeY = [0;" + maxY + "] }";
    }
}
