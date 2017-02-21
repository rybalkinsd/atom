package model;

/**
 * Created by Вьюнников Виктор on 10.10.2016.
 */
import model.GameSession;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class SingleGameSession implements GameSession{
    @NotNull
    private final Logger log = LogManager.getLogger(SingleGameSession.class);
    @NotNull
    private final List<Player> activePlayers = new ArrayList<>();
    //Для многопользовательского режима.
    //Чтобы потом не переделывать, в одиночной игре тоже будет список, только из одного игрока

    private GameField gameField;

    public SingleGameSession() {
        gameField = new GameField();
    }

    public void setGameField(GameField gameField){this.gameField=gameField;}
    public GameField getGameField(){return gameField;}


    //public SingleGameSession(Player player) {gameField = new GameField();}

    @Override
    public void join(@NotNull Player player) {
        if(FreePlace())
            activePlayers.add(player);
        //else throw
    }


    //для многопользовательского режимма
    public boolean FreePlace() {
        if(activePlayers.size() <GameConstants.MAX_PLAYERS_IN_SESSION)
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
