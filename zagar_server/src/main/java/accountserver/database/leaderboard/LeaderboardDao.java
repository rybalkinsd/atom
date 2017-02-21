package accountserver.database.leaderboard;

import accountserver.database.users.User;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by Klissan on 06.11.2016.
 * interface LeaderboardDao
 */
public interface LeaderboardDao {
    /**
     * Add user to leaderboard base, start score is 0
     *
     * @param user user to add
     */
    void addUser(@NotNull User user);

    /**
     * Remove user
     *
     * @param user user to remove
     */
    void removeUser(@NotNull User user);

    /**
     * Add score to user
     *
     * @param user       user which score will be updated
     * @param scoreToAdd score which will be added to current user`s score
     */
    void updateScore(@NotNull User user, int scoreToAdd);

    /**
     * Return top N users (descending sorted)
     *
     * @param count number of users to return
     * @return map user-score
     */
    @NotNull Map<User, Integer> getTopUsers(int count);
}
