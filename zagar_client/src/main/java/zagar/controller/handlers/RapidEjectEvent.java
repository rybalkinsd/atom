package main.java.zagar.controller.handlers;

import main.java.zagar.Game;

/**
 * Created by Klissan on 29.11.2016.
 */
public class RapidEjectEvent implements  Event  {
    @Override
    public void handle() {
        Game.rapidEject = true;
    }
}
