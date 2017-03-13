package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>>  {
    public ContactListCache(int capacity) {
        super(capacity);
    }
    HashMap<Person, List<? extends Person>> hashMap = new HashMap<>();

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        hashMap.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return hashMap.get(person);
    }

    @Override
    public int getSize() {
        return hashMap.size();

    }

    private boolean removeAny() {
        if (hashMap.size() >= capacity) {

        }
        return true;
    }

}
