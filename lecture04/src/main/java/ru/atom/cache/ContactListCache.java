package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    public ContactListCache(int capacity) {
        super(capacity);
    }

    HashMap<Person, List<? extends Person>> newMap = new HashMap<>(capacity);

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (getSize() == capacity) {
            removeAny();
        }
        newMap.put(person,people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return newMap.get(person);
    }

    @Override
    public int getSize() {
        return newMap.size();
    }

    private boolean removeAny() {
        Person key = this.newMap.keySet().iterator().next();
        return this.newMap.remove(key) != null;
    }

}
