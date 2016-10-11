package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static model.GameConstants.FOOD_MASS;
import static model.GameConstants.START_BUSH_MASS;
import static model.GameConstants.START_CELL_MASS;

/**
 * Created by Klissan on 10.10.2016.
 */
public class SinglePlayerGameManager
        implements GameManager
{
    @NotNull
    private final static Logger log = LogManager.getLogger(SinglePlayerGameManager.class);

    private final static double FIELD_SIZE = 200.0d;

    private GameField gameField;
    private List<String> leaderBoard;
    private List<GameEntity> gameEntities;
    private Player player;

    SinglePlayerGameManager(){
        this.gameField = new GameField(FIELD_SIZE);
        this.leaderBoard = new ArrayList<>();
        this.gameEntities = initialGameEntities();
        this.player = null;
    }

    /**
     * random generate
     */
    @Override
    public void join(Player player){
        this.player = player;
        this.gameEntities.add(generateNewPlayerCell(this.player));
        if (log.isInfoEnabled()) {
            log.info(player +"'s cell added to game field ");
        }
    }

    private static List<GameEntity> initialGameEntities(){
        if (log.isInfoEnabled()) {
            log.info("Start initial game session");
        }
        List<GameEntity> entities = new ArrayList<>();

        long foodCount = Math.round(FIELD_SIZE * FIELD_SIZE / 3);
        for(long i = 0; i < foodCount ; ++i ){
            entities.add(generateFood());
        }

        long bushCount = Math.round(FIELD_SIZE * FIELD_SIZE / 15);
        for(long i = 0; i < bushCount ; ++i ){
            entities.add(generateBush());
        }

        return entities;
    }

    private Cell generateNewPlayerCell(Player player){
        Point2D coordinates = generatePoint();
        Color color = GameColor.getRandomColor();
        if (log.isInfoEnabled()) {
            log.info("Cell has been generated in " + coordinates + " with color: " + color);
        }
        return new Cell(coordinates, color, START_CELL_MASS, player);
    }

    private static Food generateFood(){
        Point2D coordinates = generatePoint();
        Color color = GameColor.getRandomColor();
        if (log.isInfoEnabled()) {
            log.info("Food has been generated in " + coordinates + " with color: " + color);
        }
        return new Food(coordinates, color, FOOD_MASS);
    }

    private static Bush generateBush(){
        Point2D coordinates = generatePoint();
        final Color BUSH_COLOR= Color.GREEN;
        if (log.isInfoEnabled()) {
            log.info("Bush has been generated in " + coordinates);
        }
        return new Bush(coordinates, BUSH_COLOR, START_BUSH_MASS);
    }

    private static Point2D generatePoint(){
        double x = Math.random() * FIELD_SIZE;
        double y = Math.random() * FIELD_SIZE;
        return new Point2D.Double(x, y);
    }


    private static class GameColor
    {
        private static final int COLORS_COUNT = 10;
        private static List<Color> colors = new ArrayList<>();

        static{
            for(int i = 0; i< COLORS_COUNT; ++i){
                colors.add(generateColor());
            }
        }

        private static Color getRandomColor(){
            int i = (int) Math.round(Math.random() * 1000) % COLORS_COUNT;
            return colors.get(i);
        }

        private static Color generateColor(){
            int red   = (int) Math.round(Math.random() * 1000) % 256;
            int green = (int) Math.round(Math.random() * 1000) % 256;
            int blue  = (int) Math.round(Math.random() * 1000) % 256;
            return new Color(red, green, blue);
        }
    }


}
