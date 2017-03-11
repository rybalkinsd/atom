package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    HashMap<Person, List<? extends Person>> contactMap = new HashMap<>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        removeAny();
        contactMap.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return contactMap.get(person);
    }

    @Override
    public int getSize() {
        return contactMap.size();
    }

    private boolean removeAny() {
        Random random = new Random();
        List<Person> keys = new ArrayList<>(contactMap.keySet());
        if (getSize() >= capacity) {
            contactMap.remove(keys.get( random.nextInt(keys.size()) ));
        }

        return true;
    }

}
