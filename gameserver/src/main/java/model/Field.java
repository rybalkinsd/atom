package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public static final int BORDER_DOWN = 0;
    public static final int BORDER_TOP = 20000;
    public static final int BORDER_LEFT = 0;
    public static final int BORDER_RIGHT = 20000;

    private static final Logger log = LogManager.getLogger(Field.class);

    @NotNull
    private List<Player> players = new ArrayList<>(GameConstants.MAX_PLAYERS_IN_SESSION);

    @NotNull
    private List<Food> foods;

    @NotNull
    private List<Virus> viruses;

    @Nullable
    private List<Blob> blobs = new ArrayList<>();

    public Field(@NotNull Player player, @NotNull List<Food> foods, @NotNull List<Virus> viruses) {
        this.players.add(player);
        this.foods = foods;
        this.viruses = viruses;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Field{" +
                "border_top=" + BORDER_TOP +
                ", border_down=" + BORDER_DOWN +
                ", border_left=" + BORDER_LEFT +
                ", border_right=" + BORDER_RIGHT +
                ", players=" + players +
                ", foods=" + foods +
                ", viruses=" + viruses +
                ", blobs=" + blobs +
                '}';
    }
}
