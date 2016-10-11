package model;

/**
 * Created by Klissan on 09.10.2016.
 */


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.time.Duration;
import java.time.LocalDateTime;

import static model.GameConstants.*;

public class Cell
        extends GameEntity
{
    @NotNull
    private final static Logger log = LogManager.getLogger(Cell.class);

    private Player owner;
    private double speed;
    //private Vector direction
    private LocalDateTime shareTime;

    Cell(Point2D coordinates, Color color, int mass, Player player)
    {
        super(coordinates, color, mass);
        this.owner = player;
        this.speed = calculateSpeed(this.getMass());
        this.shareTime = LocalDateTime.now();
    }

    Player getOwner(){return owner;}
    double getSpeed(){return speed;}

    @Override
    public void setMass(int mass){
        super.setMass(mass);
        this.speed = calculateSpeed(this.getMass());
    }


    //when tapped "space" key
    Cell share(){
        //todo
        Cell cell = this;
        if(this.getMass() >= MASS_NEEDED_TO_SHARE){
            int newCellMass = super.getMass() /2;
            cell = new Cell(this.getCoordinates(), this.getColor(), newCellMass, this.getOwner());
            this.setMass(this.getMass() - newCellMass);
            this.shareTime = LocalDateTime.now();
        }
        return cell;
    }

    //lose mass over time
    void loseMass(){
        if( this.getMass() > BOARD_OF_LOSE_MASS) {
            int newMass = (int) (this.getMass() * PERCENT_OF_STABLE_MASS);
            this.setMass( newMass );
        }
    }

    //when tapped "w"
    //! в агаре в результате сплита получается cell, и куст ест cell, а еду не ест
   /* Food split(){
        if (this.getMass() >= 2 * SPLITABLE_MASS){
            this.setMass(this.getMass() - SPLITABLE_MASS);
            this.loseMass();
            return Food()
        }
        return null;
    }*/

    boolean isAvailableToSplice(){
        Duration passedTime = Duration.between(this.shareTime, LocalDateTime.now());
        return passedTime.compareTo(SPLICE_COOLDOWN) > 0;
    }

    //When 2 cell by 1 player are near
    //todo Cell splice();



    //@overload
    void eat(Food food){}
    void eat(Bush bush){}
    void eat(Cell cell){}

    private static double calculateSpeed(int mass){
        return BASE_SPEED / mass;
    }
}
