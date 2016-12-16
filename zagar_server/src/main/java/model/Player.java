package model;

import accountserver.database.users.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author apomosov
 */
public class Player {
    private final int id;
    private Field field;
    @NotNull
    private User user;
    private int windowWidth;
    private int windowHeight;

    public Player(int id, @NotNull User user) {
        this.id = id;
        this.user = user;
    }

    @NotNull
    public Field getField() {
        return field;
    }

    public void setField(@NotNull Field field) {
        this.field = field;
    }

    @NotNull
    public User getUser() {
        return user;
    }

    @NotNull
    public List<PlayerCell> getCells() {
        return field.getPlayerCells(this);
    }

    public int getTotalScore() {
        Optional<Integer> totalScore = getCells().stream()
                .map(PlayerCell::getMass)
                .reduce(Math::addExact);
        return totalScore.isPresent() ?
                totalScore.get() :
                0;
    }

    public int getId() {
        return id;
    }

    long lastMovementTime() {
        return getCells().stream()
                .map(PlayerCell::getLastMovementTime)
                .max(Long::compareTo)
                .orElse(0L);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Player) && (id == ((Player) obj).id);
    }

    @NotNull
    @Override
    public String toString() {
        return "Player{" +
                "name='" + user.getName() + '\'' +
                '}';
    }
}
