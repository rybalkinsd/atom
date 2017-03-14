package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {

    private HashMap<Person, List> friend = new HashMap<Person, List>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (friend.size() < capacity) {
            friend.put(person, people);
        } else {
            removeAny();
            friend.put(person, people);
        }
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return friend.get(person);
    }

    @Override
    public int getSize() {
        return  friend.size();
    }

    private boolean removeAny() {
        for (HashMap.Entry entry : friend.entrySet()) {
            friend.remove(entry.getKey());
            return true;
        }
        return false;
    }

}
