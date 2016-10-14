package model;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static model.GameConstants.INITIAL_FOODS_AMOUNT;
import static model.GameConstants.INITIAL_VIRUSES_AMOUNT;

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
    public void join(@NotNull Player player) {
        this.players.add(player);
        this.field.addCells(player.getCells());
        this.players.sort(new SortPlayers());
    }
}
