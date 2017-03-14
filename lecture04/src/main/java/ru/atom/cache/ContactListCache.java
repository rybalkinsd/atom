package ru.atom.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    Map<Person, List<? extends Person>> friends;

    public ContactListCache(int capacity) {
        super(capacity);
        this.friends = new HashMap<Person, List<? extends Person>>();
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (super.getCapacity() <= friends.size() && friends != null) {
            removeAny();
        }
        this.friends.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return this.friends.get(person);
    }

    @Override
    public int getSize() {
        return this.friends.size();
    }

    private boolean removeAny() {
        friends.remove(friends.keySet().toArray()[0]);
        return true;
    }

}
