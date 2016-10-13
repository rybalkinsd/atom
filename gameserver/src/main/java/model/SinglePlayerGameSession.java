package model;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by helen on 10.10.16.
 * @author helen
 * Game session for single player
 * implements GameSession
 */
public class SinglePlayerGameSession implements GameSession, GameConstants {
    @NotNull
    protected static final Logger log = LogManager.getLogger(SinglePlayerGameSession.class);

    private Player player;
    private Grid grid;
    private ArrayList<Item> gameItems;

    /**
     * Create new Single Player Game Session
     * player will join later (null value)
     */
    public SinglePlayerGameSession(){
        this.player = null;
        this.grid= new Grid(GRID_SIZE);
        this.gameItems = initGameItems();
        if (log.isInfoEnabled()) {
            log.info("SinglePlayerGameSession:"+ '{' + this +'}' + " created");
        }
    }

    /**
     * Init game items
     * Add static pellets and viruses
     * @return ArrayList<Item>
     */
    private ArrayList<Item> initGameItems(){
        ArrayList<Item> gameItems = new ArrayList<>();
        for(int i=0; i<PELLETS_NUM; i++)
            gameItems.add(new Pellet());
        for (int i=0; i<VIRUSES_NUM; i++)
            gameItems.add(new Virus());
        return gameItems;
    }

    /**
     * Player joins new game. Prepare cell for player
     * @param player player to join the game
     */
    public void join(@NotNull Player player)
    {
        this.player = player;
        this.gameItems.add(new Cell(player.getName()));
        if (log.isInfoEnabled()) {
            log.info("New cell created for " + player);
        }
    }
}