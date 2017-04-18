package ru.atom.mm.InfoGame;


import ru.atom.object.User;

import java.util.HashMap;

/**
 * Created by Fella on 18.04.2017.
 */
public class Match {
    Integer id;
    Integer gameId;
    private HashMap<String ,Integer> result = new HashMap<>(3);;

    public Match() {

    }

    public Integer getId() {
        return id;
    }

    public Match setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Match setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }


    public Match putScore(String user, Integer score){
        result.put(user,score);
        return this;
    }

    public HashMap<String,Integer> getScore(){
        return result;
    }

}
