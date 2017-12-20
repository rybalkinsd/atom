package ru.atom.matchmaker.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexandr on 25.11.2017.
 */
public class MatchBuilder {
    public int maxCount;
    private long gameId;
    private Set<String> logins;

    public MatchBuilder(int maxCount, long gameId) {
        this.maxCount = maxCount;
        this.gameId = gameId;
        logins = new HashSet<>();
    }

    public long getGameId() {
        return gameId;
    }

    public boolean putLogin(String login) {
        return logins.size() < maxCount && logins.add(login);
    }

    public boolean isReady() {
        return logins.size() == maxCount;
    }

    public Set<String> getLogins() {
        return logins;
    }
}
