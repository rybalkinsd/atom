package model.units;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Class represents player units on game field
 * @author Created by Rail on 09.10.2016.
 */

public class UnitPlayer extends Unit {
    public static final int MIN_PLAYER_MASS = 10;

    @NotNull
    private static final Logger log = LogManager.getLogger(UnitPlayer.class);

    private int speed;
    private int mass;

    UnitPlayer(int x, int y, int size){
        super(x, y, size);
        mass = size/3;
        speed = 100/(int) (Math.log(mass) / Math.log(2));
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    /**
     * Kills another player unit
     * @param unit
     */
    public void kill(UnitPlayer unit){
        mass += unit.mass;
        size += unit.size;
        speed = 100/(int) (Math.log(mass) / Math.log(2));
    }
    /**
     * Eats another unit like bait or food
     * @param unit
     */
    public void eat(Unit unit){
        size += unit.size;
        mass = size/3;
        speed = 100/(int) (Math.log(mass) / Math.log(2));
    }

    /**
     * Compares two players units mass
     * @param player second player to compare
     * @return returns 0 if their mass equal, -1 if second player mass greater, else 1
     */
    public int compare(UnitPlayer player) {
        if (player.mass == mass) return 0;
        if (player.mass > mass) return -1;
        return 1;
    }

    public static int compare(UnitPlayer player1, UnitPlayer player2){
        return player1.compare(player1);
    }

    /**
     * Create new Bait
     *  TODO Change unit mass
     * @return new UnitBait
     */
    public UnitBait bait(){
        return new UnitBait(x + 5, y + 5);
    }

    /**
     * Splits unit to two units
     * @return new UnitPlayer
     */
    public UnitPlayer split() {
        if (mass < MIN_PLAYER_MASS) {
            mass /= 2;
            size /= 2;
            speed = 100 / (int) (Math.log(mass) / Math.log(2));
            return  new UnitPlayer(x, y, size);
        }
        return null;
    }

    @Override
    public String toString() {
        return "UnitPlayer{" +
                "x='" + x + '\'' +
                "y='" + y + '\'' +
                "size='" + size + '\'' +
                "mass='" + mass + '\'' +
                "speed='" + speed + '\'' +
                '}';
    }


}
