package model;

import utils.SequentialIDGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {
    private static final Logger log = LogManager.getLogger(Player.class);
    @NotNull
    private String name;
    @NotNull
    private final List<PlayerCell> cells = new ArrayList<>();
    public static final SequentialIDGenerator idGenerator = new SequentialIDGenerator();

    public final int id;
    private int pts = 0;

    public Player(@NotNull String name, int id) {
        this.name = name;
        this.id = id;
        addCell(new PlayerCell(Cell.idGenerator.next(), new Location(0,0), name));
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @NotNull
    public List<PlayerCell> getCells() {
        return cells;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void addCell(@NotNull PlayerCell cell) {
        cells.add(cell);
    }

    public void removeCell(@NotNull PlayerCell cell) {
        cells.remove(cell);
    }

    public int getId() {
        return id;
    }

    public int getPts() {return  pts;}

    public void setPts(int pts) { this.pts = pts; }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                '}';
    }
    public void addMass(int mass){
        cells.get(0).setMass(cells.get(0).getMass()+mass);
    }
}
