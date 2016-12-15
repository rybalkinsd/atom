package tests;

import main.ApplicationContext;
import model.*;
import org.jetbrains.annotations.TestOnly;
import org.junit.Assert;
import org.junit.Test;
import ticker.Ticker;
import utils.RandomPlayerPlacer;
import utils.RandomVirusGenerator;
import utils.UniformFoodGenerator;

import static org.junit.Assert.*;

/**
 * Created by Alex on 14.12.2016.
 */
public class GameSessionImplTest {
    @Test
    public void update() throws Exception {
        Field field = new Field();
        Ticker ticker = new Ticker(1);
        UniformFoodGenerator foodGenerator = new UniformFoodGenerator(field, GameConstants.FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
        ticker.registerTickable(foodGenerator);
        GameSessionImpl GSI =  new GameSessionImpl(field, foodGenerator, new RandomPlayerPlacer(field), new RandomVirusGenerator(field, GameConstants.NUMBER_OF_VIRUSES));
        GSI.join(new Player(0, "aa"));
        Cell cell = GSI.getPlayers().get(0).getCells().get(0);
        GSI.getField().getFoods().add(new Food(cell.getX(), cell.getY()));
        GSI.getField().getFoods().add(new Food(cell.getX(), cell.getY()));
        GSI.update();
        GSI.join(new Player(1, "2"));
        GSI.getPlayers().get(1).getCells().get(0).setX(cell.getX());
        GSI.getPlayers().get(1).getCells().get(0).setY(cell.getY());
        GSI.update();
        Assert.assertEquals(GSI.getField().getFoods().size(), 0);
        Assert.assertEquals(GSI.getPlayers().get(1).getCells().size(), 0);
    }

    @Test
    public void movetest() throws Exception{
        float dx = 2.0f;
        float dy = 1.0f;
        Field field = new Field();
        Ticker ticker = new Ticker(1);
        UniformFoodGenerator foodGenerator = new UniformFoodGenerator(field, GameConstants.FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
        ticker.registerTickable(foodGenerator);
        GameSessionImpl GSI =  new GameSessionImpl(field, foodGenerator, new RandomPlayerPlacer(field), new RandomVirusGenerator(field, GameConstants.NUMBER_OF_VIRUSES));
        GSI.join(new Player(0, "aa"));

        Cell cell = GSI.getPlayers().get(0).getCells().get(0);
        System.out.println(cell.getX() + " " + cell.getY());
        double dir = Math.atan2(-dy, dx);
        int s = 2 + (int) Math.round(10 / Math.sqrt(Math.sqrt(GSI.getPlayers().get(0).getScore())));
        int x = (int) Math.round(Math.cos(dir)*s);
        int y = (int) Math.round(Math.sin(dir)*s);
        GSI.getField().getFoods().add(new Food(cell.getX() + x, cell.getY() - y));

        GSI.getPlayers().get(0).move(dx,dy);

        Cell cell2 = GSI.getPlayers().get(0).getCells().get(0);
        System.out.println(cell2.getX() + " " + cell2.getY());
        GSI.update();
        Assert.assertEquals(GSI.getField().getFoods().size(), 0);
    }

    @Test
    public void splittest() throws Exception{
        float dx = 2.0f;
        float dy = 1.0f;
        Field field = new Field();
        Ticker ticker = new Ticker(1);
        UniformFoodGenerator foodGenerator = new UniformFoodGenerator(field, GameConstants.FOOD_PER_SECOND_GENERATION, GameConstants.MAX_FOOD_ON_FIELD);
        ticker.registerTickable(foodGenerator);
        GameSessionImpl GSI =  new GameSessionImpl(field, foodGenerator, new RandomPlayerPlacer(field), new RandomVirusGenerator(field, GameConstants.NUMBER_OF_VIRUSES));
        GSI.join(new Player(0, "aa"));
        GSI.getPlayers().get(0).split();

        Assert.assertEquals(GSI.getPlayers().get(0).getCells().size(),2);
    }
}