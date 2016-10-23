package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jvnet.hk2.internal.Collector;

import java.util.*;
import java.util.stream.Collectors;

import static model.GameConstants.INITIAL_FOODS_AMOUNT;
import static model.GameConstants.INITIAL_VIRUSES_AMOUNT;
import static model.GameConstants.MAX_PLAYERS_IN_SESSION;

public class GameSession implements IGameSession {

    private UUID id;
    @NotNull
    private List<Player> players;
    @NotNull
    private Field field;

    public GameSession() {
        Set<Food> foods = new HashSet<>();
        for (int i = 0; i < INITIAL_FOODS_AMOUNT; i++) {
            foods.add(new Food(new Location()));
        }

        List<Virus> viruses = new ArrayList<>();
        for (int i = 0; i < INITIAL_VIRUSES_AMOUNT; i++) {
            viruses.add(new Virus(new Location()));
        }
        Set<Cell> cells = new HashSet<>();
        this.field = new Field(cells, foods, viruses);
        this.players = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean join(@NotNull Player player) {
        if (!isFull()) {
            this.players.add(player);
            this.field.addCells(player.getCells());
            this.players.sort(new SortPlayers());
            return true;
        }
        return false;
    }

    public boolean containsPlayer(@NotNull Player player) {
        for (Player elem : players) {
            if (elem == player) return true;
        }
        return false;
    }

    @Nullable
    private Player getPlayerById(UUID id) {
       return players.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .get();
    }

    public boolean isFull() {
        return (players.size() == MAX_PLAYERS_IN_SESSION);

    }

    public boolean leave(@NotNull UUID id) {
        Player player = this.getPlayerById(id);
       if (player != null) {
           players.remove(player);
           return true;
       }
        return false;
    }
}
