package ru.atom.cache;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * What about Map?
 */
public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {
    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        throw new NotImplementedException();
    }

    @Override
    public List<? extends Person> get(Person person) {
        throw new NotImplementedException();
    }

    @Override
    public int getSize() {
        throw new NotImplementedException();
    }

    private boolean removeAny() {
        throw new NotImplementedException();
    }

}
