package ru.atom.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListCache extends AbstractCache<Person, List<? extends Person>> {

    private HashMap<Person, List> table = new HashMap<>();

    public ContactListCache(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(Person person, List<? extends Person> people) {
        if (getSize() >= getCapacity())
            removeAny();
        table.put(person, people);
        return true;
    }

    @Override
    public List<? extends Person> get(Person person) {
        return table.get(person);
    }

    @Override
    public int getSize() {
        return table.size();
    }

    private boolean removeAny() {
        List<Person> keys = new ArrayList<>(table.keySet());
        table.remove(keys.get(0));
        return true;
    }
}
