package model;

import javafx.util.Pair;

import static model.GameConstants.MINE_INITIAL_SIZE;

/**
 * Created by svuatoslav on 10/9/16.
 */
public class Mine extends GameObject{
    private int size;

    public Mine(Pair<Double,Double> point) {
        super(point);
        size = MINE_INITIAL_SIZE;
        super.writeLog("created");
    }

    @Override
    public String toString() {
        return "Mine{" +
                "size='" + size + '\'' +
                getParam() +
                '}';
    }
}
