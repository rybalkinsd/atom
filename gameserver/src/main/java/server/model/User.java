package server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gamemodel.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class User {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);

    @NotNull private UUID userId;
    @NotNull private String name;
    @NotNull private String password;
    @Nullable private Player player;

    public User(@NotNull String name, @NotNull String password) {
        this.userId = UUID.randomUUID();
        this.password = password;
        this.name = name;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId)
                && name.equals(user.name)
                && password.equals(user.password)
                && (player != null ? player.equals(user.player) : user.player == null);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
