package model;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.random;
import static model.GameConstants.*;

/**
 * Created by svuatoslav on 10/9/16.
 */
public class Field {
    @NotNull
    private static final Logger log = LogManager.getLogger(Field.class);
    @NotNull
    List<GameObject> gameObjects;
    double Width;
    double Height;

    public Field() {
        Height=HEIGHT;
        Width=WIDTH;
        gameObjects = new ArrayList<GameObject>();
        if (log.isInfoEnabled()) {
            log.info(this + " created");
        }

    }

    public void init()
    {
        gameObjects.clear();
        for(int i=0;i<INITIAL_NUMBER_OF_FOOD;i++)
            gameObjects.add(new Food(new Pair<Double,Double>(Width*random(),Height*random())));
        for(int i=0;i<INITIAL_NUMBER_OF_MINES;i++)
            gameObjects.add(new Mine(new Pair<Double,Double>(Width*random(),Height*random())));
        if (log.isInfoEnabled()) {
            log.info(this + " initialized");
        }
    }

    public void addPlayer(Player player)
    {
        PlayerBody body = new PlayerBody(player,new Pair<Double,Double>(Width*random(),Height*random()));
        gameObjects.add(body);
        player.addBody(body);
    }

    @Override
    public String toString() {
        String res = "Field{" +
                "Height='" + Height + '\'' +
                "Width='" + Width + '\'' +
                "Objects='" + gameObjects.size() + '\'' +
                '}';
        return res;
    }
}
