package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//main class for every game session
public class GameSpace implements GameSession{
    @NotNull
    private final Logger log = LogManager.getLogger(GameSpace.class);

    //containers for holding game objects
    @NotNull
    private ArrayList<Food> food;
    @NotNull
    private ArrayList<Thorn> thorns;
    @NotNull
    private ArrayList<Player> players;

    public GameSpace (){
        try {
            food = new ArrayList<>();
            thorns = new ArrayList<>();
            players = new ArrayList<>();
        }
        catch (Throwable e){
            if (log.isInfoEnabled()) {
                log.info("Empty GameSpace was not created because of error");
            }
        }
        if (log.isInfoEnabled()) {
            log.info("Empty "+this+" created");
        }
    }

    public void join(@NotNull Player _player){
        players.add(_player);
    }
    public void addFood(@NotNull Food new_food) { food.add(new_food); }
    public void addThorn(@NotNull Thorn new_thorn) {thorns.add(new_thorn); }

    @Override
    public String toString(){
        return "GameSpace{current number of GameObjects: players("+players.size()+"),food("+food.size()+"),thorns("+thorns.size()+")}";
    }
}
