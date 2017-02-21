package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Orlov on 11.10.2016.
 */


public class GameField {
    static private double fieldWidth;
    static private double fieldHeight;
    @NotNull
    private List<GameObject> gameObjectsContainer = new ArrayList<>();

    {
        fieldHeight = GameConstants.FIELD_HEIGHT;
        fieldWidth = GameConstants.FIELD_WIDTH;
    }

    public List<GameObject> getgameObjectsContainer() {return new ArrayList<>(this.gameObjectsContainer);}

    public void addToContainer(){}

    @Override
    public String toString(){return "(Plaing)";}

}
