package accountserver.database.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xakep666 on 12.10.16.
 * <p>
 * DataBase uses memory to keep user data
 */
public class InMemoryUsersStorage implements UserDao {
    @NotNull
    private static Logger log = LogManager.getLogger(InMemoryUsersStorage.class);

    @NotNull
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    public InMemoryUsersStorage() {
        log.info("Created in-memory users storage");
    }

    @Override
    public void addUser(@NotNull User user) {
        users.put(user.getId(), user);
    }

    @Override
    public @Nullable User getUserById(int id) {
        return users.get(id);
    }

    @Override
    public @Nullable User getUserByName(@NotNull String name) {
        for (User user : users.values()) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    @Override
    public void removeUser(@NotNull User user) {
        users.remove(user.getId());
    }

    @Override
    public @NotNull List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void updateUser(@NotNull User user) {

    }
}
