package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Rail on 11.10.2016.
 */
public class GameSessionImpl implements GameSession {
    ArrayList<Player> players = new ArrayList<>();
    GameField field;
    @Override
    public void join(@NotNull Player player) {
        if (players.size() < GameConstants.MAX_PLAYERS_IN_SESSION) {
            players.add(player);
        }
    }

    @Override
    public int getPlayersCount() {
        return players.size();
    }

    public void quit(@NotNull Player player) {
        players.remove(player);
    }

    public GameSessionImpl(){
        field = new GameField();
    }
}
