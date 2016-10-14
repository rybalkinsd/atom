package model;
/**
 * Created by Orlov on 11.10.2016.
 */


import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class ImplGameSession implements GameSession{
    @NotNull
    private final Logger log = LogManager.getLogger(ImplGameSession.class);
    @NotNull
    private final List<Player> activePlayers = new ArrayList<>();
    @NotNull
    private GameField gameField;

    public ImplGameSession() {
        gameField = new GameField();
    }

    public void setGameField(GameField gameField){this.gameField=gameField;}
    public GameField getGameField(){return gameField;}
    int id;




    @Override
    public void join(@NotNull Player player) {
            activePlayers.add(player);
    }
    @Override
    public void disconnect(@NotNull Player player){
        activePlayers.remove(player);
    }


    @Override
    public boolean FreePlace() {
        if(activePlayers.size() < GameConstants.MAX_PLAYERS_IN_SESSION)
            return true;
        else
            return false;

    }

    @Override
    public String toString() {
        return "GameSession{" +
                "ID='1'"  +
                '}';
    }
}
