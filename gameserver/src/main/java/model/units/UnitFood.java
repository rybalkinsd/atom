package model.units;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Created by Rail on 10.10.2016.
 */
/**
 * Class represents food unit on game field
 */
public class UnitFood extends Unit{
    public static final int FOOD_SIZE = 2;
    protected static final Logger log = LogManager.getLogger(UnitFood.class);

    public UnitFood(int x, int y){
        super(x, y, FOOD_SIZE);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "UnitBush{" +
                "x='" + x + '\'' +
                "y='" + y + '\'' +
                "size='" + FOOD_SIZE + '\'' +
                '}';
    }
}
