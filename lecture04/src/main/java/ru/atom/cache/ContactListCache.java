package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * What about Map?//почитать
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private int size = 0;
    public Map<Person, List<? extends Person>> userList;

    public ContactListCache(int capacity) {
        super(capacity);
        userList = new HashMap<Person, List<? extends Person>>(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (size == capacity) removeAny();
        userList.put(person, people);
        size++;
        return true;

    }

    @Override
    public List<? extends Person> get(Person person) {
        return userList.get(person);

    }

    @Override
    public int getSize() {
        return size;

    }

    private boolean removeAny() {
        for (Person key: userList.keySet()) {
            userList.remove(key);
            size--;
            return true;
        }
        return false;
    }

}