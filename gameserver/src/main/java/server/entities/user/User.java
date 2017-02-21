package server.entities.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.session.GameSession;

import java.util.UUID;

public class User {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);

    @NotNull private UUID userID;
    @NotNull private String name;
    @NotNull private String password;
    @Nullable private Player player;
    @Nullable private GameSession session;

    public User(@NotNull String name, @NotNull String password) {
        this.userID = UUID.randomUUID();
        this.name = name;
        this.password = password;
        if (log.isInfoEnabled()) {
            log.info(this + " created");
        }
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Nullable
    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable Player player) {
        this.player = player;
    }

    public boolean checkPassword(@NotNull String pass) {
        return password.equals(pass);
    }

    @Override
    public String toString() {
        return "User(" +
                "userID=" + userID +
                ", name=" + name +
                ", password=" + password +
                ')';
    }

    @Override
    public boolean equals(Object that) {
        if (that == null || that.getClass() != getClass()) return false;
        if (this == that) return true;

        User newUser = (User) that;
        return this.name.equals(newUser.name) &&
                this.userID.equals(newUser.userID) &&
                this.password.equals(newUser.password);
    }

    @Override
    public int hashCode() {
        int result = userID.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }

}
