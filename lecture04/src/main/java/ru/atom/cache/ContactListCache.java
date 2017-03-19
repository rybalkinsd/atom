package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.HashMap;
import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person,List> friends = new HashMap<>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (friends.size() < capacity) {
            friends.put(person,people);
        } else {
            removeAny();
            friends.put(person,people);
        }
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return friends.get(person);
    }

    @Override
    public int getSize() {
        return friends.size();
    }


    private boolean removeAny() {
        for (HashMap.Entry entry : friends.entrySet()) {
            friends.remove(entry.getKey());
            return true;
        }
        return false;
    }

}
