package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klissan on 09.10.2016.
 */
public class SinglePlayerGameSession
        implements GameSession
{
    @NotNull
    private final Logger log = LogManager.getLogger(SinglePlayerGameSession.class);

    private Player player;
    private GameManager gameManager;

    public SinglePlayerGameSession(){
        player = null;
        this.gameManager = new SinglePlayerGameManager();
    }

    /**
     * Game session creates whenever player start playing
     *
     * @param player player to join the game
     */
    @Override
    public void join(@NotNull Player player) {
        this.player = player;
        gameManager.join(player);
    }

    @Override
    public String toString(){
        return "Single Player Game Session with player: " + player;
    }
}
