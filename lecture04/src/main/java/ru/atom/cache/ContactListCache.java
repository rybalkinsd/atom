package ru.atom.cache;


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
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends Person> get(Person person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException();
    }

    private boolean removeAny() {
        throw new UnsupportedOperationException();
    }

}
