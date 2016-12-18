package model;

/**
 * Created by User on 10.10.2016.
 */

public class Food extends Cell {

    public Food(Location location){
        super(location, GameConstants.FOOD_MASS);
    }
}
