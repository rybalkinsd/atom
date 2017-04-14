package ru.atom.dbhackaton.resource;

import java.util.Map;

/**
 * Created by BBPax on 24.03.17.
 */
public class TokenStorage extends AbstractStorage<Long, Token> {
    public Long getToken(User user) {
        for (Map.Entry<Long, Token> entry : memory.entrySet()) {
            if (entry.getValue().getUser().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean validToken(String token) {
        Long tokenN = Long.parseLong(token);
        return memory.get(tokenN) != null;
    }

    public TokenStorage setUp() {
        return this;
    }

    @Override
    public Token remove(Long aLong) throws NullPointerException {
        if (memory.get(aLong) == null) {
            throw new NullPointerException("not logined");
        }
        return super.remove(aLong);
    }
}
