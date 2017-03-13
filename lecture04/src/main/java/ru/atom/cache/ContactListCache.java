package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {

    HashMap<Person, List<? extends Person>> map;

    public ContactListCache(int capacity) {
        super(capacity);
        map = new HashMap<>();
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (getSize() < capacity) {
            map.put(person, people);
            return true;
        } else {
            removeAny();
            map.put(person, people);
            return false;
        }
    }

    @Override
    public List<? extends Person> get(Person person) {
        if (map.containsKey(person)) {
            return map.get(person);
        } else {
            return null;
        }
    }

    @Override
    public int getSize() {
        return map.size();
    }

    private boolean removeAny() {
        for (Person p : map.keySet()) {
            map.remove(p);
            break;
        }
        return true;
    }

}
