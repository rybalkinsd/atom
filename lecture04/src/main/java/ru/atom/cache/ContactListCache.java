package ru.atom.cache;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List<? extends Person>>  cacheMap;

    public ContactListCache(int capacity) {
        super(capacity);
        this.cacheMap = new HashMap<>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (cacheMap.size() >= capacity)
            removeAny();
        cacheMap.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return cacheMap.get(person);
    }



    @Override
    public int getSize() {
        return cacheMap.size();
    }

    private boolean removeAny() {
        if (cacheMap.size() > 0) {
            Person key = cacheMap.keySet().iterator().next();
            return cacheMap.remove(key) != null;
        }
        return false;
    }

}
