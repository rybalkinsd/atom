package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;


/**
 * Created by svuatoslav on 10/9/16.
 */
public class Food extends GameObject {

    public Food(Pair<Double,Double> point) {
        super(point);
        super.writeLog("created");
    }

    public String toString() {
        return "Food{" +
                getParam() +
                '}';
    }
}
