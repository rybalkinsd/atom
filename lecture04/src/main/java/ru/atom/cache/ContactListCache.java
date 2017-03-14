package ru.atom.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List> friends = new HashMap<Person, List>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (friends.size() < capacity) {
            friends.put(person, people);
        } else {
            removeAny();
            friends.put(person, people);
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
        List<Person> keysAsArray = new ArrayList<Person>(friends.keySet());
        friends.remove(keysAsArray.get(0));
        return true;
    }
}
