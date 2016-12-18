package accountserver.database.tokens;

import accountserver.database.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xakep666 on 24.10.16.
 * <p>
 * Tokens storage based on in-memory data structures
 */
public class InMemoryTokensStorage implements TokenDao {
    @NotNull
    private static Logger log = LogManager.getLogger(User.class);

    @NotNull
    private Map<Token, User> tokenOwners = new ConcurrentHashMap<>();
    @NotNull
    private Map<User, Token> userTokens = new ConcurrentHashMap<>();

    private Thread prThread;

    public InMemoryTokensStorage() {
        prThread = new Thread(this::periodicRemover);
        log.info("In-memory tokens storage created");
        prThread.start();
    }

    @Override
    @NotNull
    public Token generateToken(@NotNull User user) {
        if (userTokens.containsKey(user)) {
            Token t = userTokens.get(user);
            if (t.isValid()) return t;
        }
        Token t = new Token();
        userTokens.put(user, t);
        tokenOwners.put(t, user);
        return t;
    }

    @Override
    @Nullable
    public Token getUserToken(@NotNull User user) {
        if (!userTokens.containsKey(user)) {
            return null;
        }
        return userTokens.get(user);
    }

    @Override
    @Nullable
    public User getTokenOwner(@NotNull Token token) {
        if (!tokenOwners.containsKey(token)) return null;
        User owner = tokenOwners.get(token);
        if (owner == null) return null;
        if (!token.isValid()) return null;
        return owner;
    }

    @Override
    @NotNull
    public List<User> getValidTokenOwners() {
        List<User> ret = new ArrayList<>(userTokens.size());
        userTokens.forEach((User key, Token value) -> {
            if (value.isValid()) ret.add(key);
        });
        return ret;
    }

    @Nullable
    @Contract("null -> null")
    @Override
    public Token findByValue(@Nullable String rawToken) {
        for (Token token : userTokens.values()) {
            if (token.rawEquals(rawToken) && token.isValid()) return token;
        }
        return null;
    }

    @Override
    public void removeToken(@NotNull Token token) {
        User owner = tokenOwners.get(token);
        if (owner != null) {
            tokenOwners.remove(token);
            userTokens.remove(owner);
        }
    }

    @Override
    public void removeToken(@NotNull User user) {
        Token token = userTokens.get(user);
        if (token != null) {
            userTokens.remove(user);
            tokenOwners.remove(token);
        }
    }

    private void periodicRemover() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Set<User> invalidTokenOwners = new HashSet<>();
                Set<Token> invalidTokens = new HashSet<>();
                userTokens.forEach((User key, Token value) -> {
                    if (value.isValid()) {
                        invalidTokenOwners.add(key);
                        invalidTokens.add(value);
                    }
                });
                invalidTokenOwners.forEach(userTokens::remove);
                invalidTokens.forEach(tokenOwners::remove);
                Thread.sleep(TOKEN_REMOVAL_INTERVAL.toMillis());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void finalize() {
        try {
            super.finalize();
            prThread.interrupt();
        } catch (Throwable ignored) {
        }
    }
}
