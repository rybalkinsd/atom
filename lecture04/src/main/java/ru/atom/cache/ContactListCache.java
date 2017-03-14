package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    public ContactListCache(int capacity) {
        super(capacity);
        this.rep = new HashMap();
    }

    private HashMap<Person, List<? extends Person>> rep;

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (rep.size() < capacity) {
            rep.put(person, people);
        } else {
            removeAny();
            rep.put(person, people);
        }
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return rep.get(person);
    }

    @Override
    public int getSize() {
        return rep.size();
    }

    private boolean removeAny() {
        rep.remove(rep.keySet().toArray()[0]);
        return true;
    }

}
