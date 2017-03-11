package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private int size = 0;
    public Map<Person, List<? extends Person>> friendList;

    public ContactListCache(int capacity) {
        super(capacity);
        friendList = new HashMap<Person, List<? extends Person>>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (size == capacity) removeAny();
        friendList.put(person, people);
        size++;
        return true;
        //throw new NotImplementedException();
    }

    @Override
    public List<? extends Person> get(Person person) {
        return friendList.get(person);
        //throw new NotImplementedException();
    }

    @Override
    public int getSize() {
        return size;
        //throw new NotImplementedException();
    }

    private boolean removeAny() {
        for (Person key: friendList.keySet()) {
            friendList.remove(key);
            size--;
            return true;
        }
        return false;
        //throw new NotImplementedException();
    }

}
