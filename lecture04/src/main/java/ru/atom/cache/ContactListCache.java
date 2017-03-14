package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private int size;
    private int capasity;
    private HashMap<Person, List<? extends Person>> ourCacheMap;

    public ContactListCache(int capacity) {
        super(capacity);
        this.size = 0;
        this.capasity = capacity;
        ourCacheMap = new HashMap<>();
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (ourCacheMap.containsKey(person)) {
            return false;
        }
        if (size < capasity) {
            ourCacheMap.put(person, people);
            size++;
            return true;
        } else {
            removeAny();
            ourCacheMap.put(person, people);
            size++;
            return true;
        }
    }

    @Override
    public List<? extends Person> get(Person person) {
        if (!ourCacheMap.containsKey(person)) {
            return null;
        }
        List<? extends Person> persons = new ArrayList<>();
        for (HashMap.Entry entry :
                ourCacheMap.entrySet()) {
            if (entry.getKey() == person) {
                persons = (List<? extends Person>) entry.getValue();
            }
        }
        return persons;
    }

    @Override
    public int getSize() {
        return size;
    }

    private boolean removeAny() {
        for (HashMap.Entry entry :
                ourCacheMap.entrySet()) {
            ourCacheMap.remove(entry.getKey());
            size--;
            return true;
        }
        return false;
    }

}
