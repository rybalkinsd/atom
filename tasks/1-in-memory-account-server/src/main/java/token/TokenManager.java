package token;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import resources.User;

/**
 * Created by Robin on 28.03.2017.
 */
public class TokenManager {
    private ConcurrentHashMap<Token, User> tokens;

    public TokenManager() {
        this.tokens = new ConcurrentHashMap<>();
    }

    public boolean containsKey(Token token) {
        return tokens.containsKey(token);
    }

    public void put(Token token, User user) {
        tokens.put(token, user);
    }

    public void remove(Token token) {
        tokens.remove(token);
    }

    public Collection<User> returnAllUsers() {
        return tokens.values();
    }
}
