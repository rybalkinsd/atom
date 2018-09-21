package ru.atom.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    Map<Person, List<? extends Person>> cache;

    public ContactListCache(int capacity) {
        super(capacity);
        cache = new ConcurrentHashMap<>(this.capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        cache.put(person, people);
        capacity = getSize();
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return cache.get(person);
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    private boolean removeAny() {
        cache.clear();
        capacity = getSize();
        return true;
    }
}