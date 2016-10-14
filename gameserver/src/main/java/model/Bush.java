package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.geom.Point2D;

import static model.GameConstants.BUSH_CRITICAL_MASS;

/**
 * Created by Klissan on 09.10.2016.
 */
public class Bush
        extends GameEntity
{
    @NotNull
    private final static Logger log = LogManager.getLogger(Bush.class);


    Bush(Point2D coordinates, Color color, int mass) {
        super(coordinates, color, mass);
    }

    void eat(Food food){
        //todo if overeat then  share
    }

    boolean isOvereat(){return this.getMass() <= BUSH_CRITICAL_MASS;}


    @Override
    public String toString(){
        return "Bush: " + super.toString() + " }";
    }
}
