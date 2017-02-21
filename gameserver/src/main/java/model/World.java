package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class World {

    @NotNull
    private static final Logger log = LogManager.getLogger(World.class);

    @NotNull
    private List<Player> players = new ArrayList<>(GameConstants.MAX_PLAYERS_IN_SESSION);

    @NotNull
    private List<Food> foods;

    @NotNull
    private List<Virus> viruses;

    public World(@NotNull List<Food> foods, @NotNull List<Virus> viruses) {
        this.foods = foods;
        this.viruses = viruses;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(@NotNull Player player) {
        players.add(player);
    }

    @Override
    public String toString() {
        return "World(" +
                "players=" + players +
                ", foods=" + foods +
                ", viruses=" + viruses +
                ')';
    }

}
