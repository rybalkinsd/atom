package accountserver.database.tokens;

import accountserver.database.users.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;

/**
 * Created by xakep666 on 24.10.16.
 * <p>
 * Provides abstraction layer for tokens storage
 * Issues, stores, validates {@link Token}
 */
public interface TokenDao {
    /**
     * Time interval for periodic removing of invalid tokens
     */
    Duration TOKEN_REMOVAL_INTERVAL = Duration.ofHours(2);

    /**
     * Issues a new token (if was not found or invalid) or returns alrady issued
     *
     * @param user user who wants token
     * @return found or issued token
     */
    @NotNull
    Token generateToken(@NotNull User user);

    /**
     * Finds user`s token
     *
     * @param user user which token will be searched
     * @return user`s token if it`s found and it is valid, null otherwise
     */
    @Nullable
    Token getUserToken(@NotNull User user);

    /**
     * Finds token owner
     *
     * @param token token which owner will be searched
     * @return user, null if not found
     */
    @Nullable
    User getTokenOwner(@NotNull Token token);

    /**
     * Find given raw token in storage
     *
     * @param rawToken token to find
     * @return Token object if it was found and valid, null otherwise
     */
    @Nullable
    @Contract("null -> null")
    Token findByValue(@Nullable String rawToken);

    /**
     * @return list of users with valid tokens
     */
    @NotNull
    List<User> getValidTokenOwners();

    /**
     * Removes token from storage
     *
     * @param token token to remove
     */
    void removeToken(@NotNull Token token);

    /**
     * Removes user`s token from storage
     *
     * @param user user which token will be removed
     */
    void removeToken(@NotNull User user);
}
