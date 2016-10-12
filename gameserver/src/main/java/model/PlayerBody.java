package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import static model.GameConstants.INITIAL_SIZE;

/**
 * Created by svuatoslav on 10/9/16.
 */
public class PlayerBody extends GameObject {
    @NotNull
    private int size;
    private Player player;

    public  PlayerBody(Player player){
        this.player=player;
        size = INITIAL_SIZE;
        writeLog("created");
    }

    public  PlayerBody(Player player,Pair<Double,Double>point){
        super(point);
        this.player=player;
        size = INITIAL_SIZE;
        writeLog("created");
    }

    public int getSize()
    {return size;}

    @Override
    public String toString() {
        return "PlayerBody{" +
                "owner='"+ player.getName() + '\'' +
                "size='" + size + '\'' +
                getParam() +
                '}';
    }
}
