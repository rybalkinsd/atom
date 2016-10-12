package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class World {

    @NotNull
    private static final Logger log = LogManager.getLogger(World.class);

    @NotNull
    private Set<Player> players = new HashSet<>(GameConstants.MAX_PLAYERS_IN_SESSION);

    @NotNull
    private Set<Food> foods;

    @NotNull
    private Set<Virus> viruses;

    public World(@NotNull Player player, @NotNull Set<Food> foods, @NotNull Set<Virus> viruses) {
        this.players.add(player);
        this.foods = foods;
        this.viruses = viruses;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
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
