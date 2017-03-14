package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List<? extends Person>> map;

    public ContactListCache(int capacity) {
        super(capacity);
        this.map = new HashMap<>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (map.size() >= capacity)
            removeAny();
        map.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return map.get(person);
    }

    @Override
    public int getSize() {
        return map.size();
    }

    private boolean removeAny() {
        if (map.size() > 0) {
            Person key = map.keySet().iterator().next();
            return map.remove(key) != null;
        }
        return false;
    }

}
