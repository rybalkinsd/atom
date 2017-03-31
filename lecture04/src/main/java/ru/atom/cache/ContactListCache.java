package ru.atom.cache;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>>  {

    private HashMap<Person, List<? extends Person>> hashMap = new HashMap<>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        removeAny();
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
            Person person = hashMap.keySet().iterator().next();
            return hashMap.remove(person) != null;
        }
        return true;
    }

}
