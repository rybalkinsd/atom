package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.NotNull;
import server.entities.user.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);

    @NotNull
    private User user;

    @NotNull
    private List<Cell> cells = new ArrayList<>(GameConstants.MAX_CELLS);


    public Player(@NotNull User user) {
        this.user = user;
        Cell initialCell = new Cell(
                Color.getHSBColor(new Random().nextFloat(),
                new Random().nextFloat(), new Random().nextFloat()),
                new Position(new Random().nextDouble() * GameConstants.MAX_BORDER_RIGHT,
                        new Random().nextDouble() * GameConstants.MAX_BORDER_TOP));
        this.cells.add(initialCell);
        if (log.isInfoEnabled()) {
          log.info(toString() + " created");
        }
    }

    @NotNull
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Player{" + "name='" + user.getName() +
                ", cells=" + cells +
                '}';
    }
}
