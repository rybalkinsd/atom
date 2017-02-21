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
    private Player player;

    SinglePlayerGameManager(){
        this.gameField = new GameField(FIELD_SIZE);
        this.leaderBoard = new ArrayList<>();
        initializationGameEntities();
        this.player = null;
    }

    /**
     * Add player
     */
    @Override
    public void join(Player player){
        this.player = player;
        this.gameField.add(generateNewPlayerCell(this.player));
        if (log.isInfoEnabled()) {
            log.info(player +"'s cell added to game field ");
        }
    }

    /**
     * Random generated entities for Game Field
     */
    private void initializationGameEntities(){
        if (log.isInfoEnabled()) {
            log.info("Start initial game session");
        }

        long foodCount = Math.round(FIELD_SIZE * FIELD_SIZE / 3);
        for(long i = 0; i < foodCount ; ++i ){
            this.gameField.add(generateFood());
        }

        long bushCount = Math.round(FIELD_SIZE * FIELD_SIZE / 15);
        for(long i = 0; i < bushCount ; ++i ){
            this.gameField.add(generateBush());
        }
    }

    /**
     * Generate player cell with mass = START_CELL_MASS, random coordinates and color
     * @param player - owner of this cell
     * @return player's Cell
     */
    private Cell generateNewPlayerCell(Player player){
        Point2D coordinates = generatePoint();
        Color color = GameColor.getRandomColor();
        Cell cell = new Cell(coordinates, color, START_CELL_MASS, player);
        if (log.isInfoEnabled()) {
            log.info("Has been generated " + cell);
        }
        return cell;
    }

    /**
     * Generate Food with mass = FOOD_MASS, random coordinate and color
     * @return Food
     */
    private static Food generateFood(){
        Point2D coordinates = generatePoint();
        Color color = GameColor.getRandomColor();
        Food food = new Food(coordinates, color, FOOD_MASS);
        if (log.isInfoEnabled()) {
            log.info("Has been generated " + food);
        }
        return food;
    }

    /**
     * Generate green with mass = START_BUSH_MASS Bush with random coordinates
     * @return Bush
     */
    private static Bush generateBush(){
        Point2D coordinates = generatePoint();
        final Color BUSH_COLOR= Color.GREEN;
        Bush bush = new Bush(coordinates, BUSH_COLOR, START_BUSH_MASS);
        if (log.isInfoEnabled()) {
            log.info("Has been generated " + bush);
        }
        return bush;
    }

    /**
     * Generate point with random coordinates in range [0; FIELD_SIZE)
     * @return Point2D
     */
    private static Point2D generatePoint(){
        double x = Math.random() * FIELD_SIZE;
        double y = Math.random() * FIELD_SIZE;
        return new Point2D.Double(x, y);
    }

    /**
     * Class for generate colors of  game entities
     */
    private static class GameColor
    {
        private static final int COLORS_COUNT = 10;
        private static List<Color> colors = new ArrayList<>();

        //Generate COLORS_COUNT RGB colors on start of program
        static{
            for(int i = 0; i< COLORS_COUNT; ++i){
                colors.add(generateColor());
            }
        }

        /**
         *
         * @return color from inner random array of colors,
         * which randomly creates in start of program
         */
        private static Color getRandomColor(){
            int i = (int) Math.round(Math.random() * 1000) % COLORS_COUNT;
            return colors.get(i);
        }

        /**
         * Generate random RGB color
         * @return RGB color
         */
        private static Color generateColor(){
            int red   = (int) Math.round(Math.random() * 1000) % 256;
            int green = (int) Math.round(Math.random() * 1000) % 256;
            int blue  = (int) Math.round(Math.random() * 1000) % 256;
            return new Color(red, green, blue);
        }
    }


}
