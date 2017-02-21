package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Вьюнников Виктор on 11.10.2016.
 */
//Класс неподвижных объектов, которые разбросаны по игровому полю. могут быть выброшены другими игроками (клавиша "W")
public class FoodObject extends GameObject {

    private double value; //какое то число которое прибавляется к радиусу после поглощения

    public double getValue(){return value;}

    public void setValue(double value){this.value=value;}

    @Override
    public String toString() {
        return "FoodObject::" +super.toString();
    }
}
