package ru.atom.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by konstantin on 29.03.17.
 */
public class TockenList {
    private ConcurrentHashMap<Tocken, User> tockens;

    public TockenList() {
        this.tockens = new ConcurrentHashMap<>();
    }

    public User getUser(Tocken tocken) {
        if (tockens.containsKey(tocken)) {
            return tockens.get(tocken);
        }
        return null;
    }

    public boolean containsKey(Tocken tocken) {
        return tockens.containsKey(tocken);
    }

    public String getUsers() {
        String str = "{ users: [";
        if (tockens.size() > 0) {
            for (Map.Entry<Tocken, User> userTockenEntry: tockens.entrySet()) {
                if (userTockenEntry.getKey() != null) {
                    str = str + userTockenEntry.getValue().toString() + ", ";
                }
            }
            str = str.substring(0, str.lastIndexOf(","));
        }
        str = str + "]}";
        return str;
    }

    public void put(Tocken tocken, User user) {
        tockens.put(tocken, user);
    }

    public void remove(Tocken token) {
        tockens.remove(token);
    }
}
