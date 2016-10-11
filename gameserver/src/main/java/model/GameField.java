package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Klissan on 09.10.2016.
 */

//have size from 0 to maxX and from 0 to maxY
public class GameField {
    @NotNull
    private final Logger log = LogManager.getLogger(GameField.class);

    private double maxX;
    private double maxY;


    GameField(double fieldSize ){};
}
