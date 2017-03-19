package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.HashMap;

/**
 * What about Map?
 */

public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    private HashMap<Person, List<? extends Person>> personList = new HashMap<>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (personList.size() > capacity - 1) {
            this.removeAny();
        }
        personList.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return this.personList.get(person);
    }

    @Override
    public int getSize() {
        return this.personList.size();
    }

    private boolean removeAny() {
        this.personList.remove(this.personList.keySet().iterator().next());
        return true;
    }

}
