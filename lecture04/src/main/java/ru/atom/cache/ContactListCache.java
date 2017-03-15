package ru.atom.cache;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List<? extends Person>> friends;

    public ContactListCache(int capacity) {
        super(capacity);
        this.friends = new HashMap<>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (friends.size() >= capacity) {
            removeAny();
        }
        friends.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return friends.get(person);
    }

    @Override
    public int getSize() {
        return friends.size();
    }

    private boolean removeAny() {
        if (friends.size() > 0) {
            Person key = this.friends.keySet().iterator().next();
            return this.friends.remove(key) != null;
        }
        return false;
    }

}
