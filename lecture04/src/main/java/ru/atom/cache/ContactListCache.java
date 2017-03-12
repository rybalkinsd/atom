package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private final Map<Person, List<? extends Person>> hashmap = new HashMap<>(capacity);

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (hashmap.size() < capacity) {
            hashmap.put(person, people);
        } else {
            removeAny();
            hashmap.put(person, people);
        }
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return hashmap.get(person);
    }

    @Override
    public int getSize() {
        return hashmap.size();
    }

    private boolean removeAny() {
        Person remove = null;
        for (Person key : hashmap.keySet()) {
            remove = key;
            break;
        }
        hashmap.remove(remove);
        return  true;
    }

}
