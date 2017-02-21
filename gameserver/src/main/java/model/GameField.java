package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вьюнников Виктор on 11.10.2016.
 */
//игровое поле
public class GameField {
    static private double fieldWidth;
    static private double fieldHeight;

    private List<GameObject> gameObjectsContainer = new ArrayList<>();
    //какой то контейнер с игровыми обхектами на поле, FoodObject PoisonObject
    //скорее всего будет хеш таблица, пока что неважно
    {
        fieldHeight = GameConstants.FIELD_HEIGHT;
        fieldWidth = GameConstants.FIELD_WIDTH;
    }

    public List<GameObject> getgameObjectsContainer() {return new ArrayList<>(this.gameObjectsContainer);}

    public void addToContainer(){}

    @Override
    public String toString(){return "(Plaing)";}

}
