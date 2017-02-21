package accountserver.database.users;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by xakep666 on 12.10.16.
 * <p>
 * Provides an abstraction layer for databases of {@link User}
 */
public interface UserDao {
    /**
     * Add user to storage
     *
     * @param user user to add
     */
    void addUser(@NotNull User user);

    /**
     * Finds user by user id
     *
     * @param id user id to find
     * @return User object if found null otherwise
     */
    @Nullable
    User getUserById(int id);

    /**
     * Find user by username
     *
     * @param name name of user to find
     * @return User object if found, null otherwise
     */
    @Nullable
    User getUserByName(@NotNull String name);

    /**
     * Remove user from base;
     *
     * @param user user to remove
     */
    void removeUser(@NotNull User user);

    /**
     * @return List of all registered users
     */
    @NotNull
    List<User> getAllUsers();

    /**
     * Update user info in storage
     *
     * @param user user which info will be updated
     */
    void updateUser(@NotNull User user);
}
