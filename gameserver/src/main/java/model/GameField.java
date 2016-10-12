package model;

import model.factory.UnitBushFactory;
import model.factory.UnitFoodFactory;
import model.units.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Rail on 09.10.2016.
 */
public class GameField {
    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);
     private ArrayList<Unit> units;
    public GameField()
    {
        units.addAll(UnitFoodFactory.getFood(1000));
        units.addAll(UnitBushFactory.getFood(50));
    }
}
